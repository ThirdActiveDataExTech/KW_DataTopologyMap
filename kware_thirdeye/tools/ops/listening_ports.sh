#!/bin/bash
# 서버에서 듣고 있는 포트를 프로그램(PID) 기준으로 묶어 보여 준다: 포트 전부, 실행 계정, 실행 파일, 작업 폴더, 시작 시각, 명령.
# docker 로 연 포트는 컨테이너별로 묶어 이미지·compose 폴더·컨테이너 안 계정을 보여 준다.
# 시스템 데몬(ssh·systemd·DNS·시간 동기 등)과 root 가 /usr/sbin·/usr/lib·/lib·/sbin 에서 띄운 것은 숨긴다.
# 사용: sudo 권한이 있는 계정에서  ./listening_ports.sh   (모두 보려면 SYSTEM='^$' 로 바꾸고 숨김 두 줄을 지운다)
# 실행 파일에 "(deleted)" 가 붙으면 프로세스가 뜬 뒤 그 파일이 교체·삭제된 것(패키지 갱신 등) → 재시작 대상.
SYSTEM='^(sshd|systemd.*|dhclient|dhcpcd|cupsd|cups-browsed|rpcbind|rpc\..*|chronyd|ntpd|avahi-daemon|dnsmasq|NetworkManager|wpa_supplicant|exim4|postfix|master|snapd|containerd|dockerd|rsyslogd|sssd.*|nmbd|smbd|xrdp.*|unbound|named)$'
declare -A PORTS PROG
while read -r proto _ _ _ local _ users; do
    pid=$(echo "$users" | grep -oE 'pid=[0-9]+' | head -1 | cut -d= -f2); [ -z "$pid" ] && continue
    prog=$(echo "$users" | grep -oE '\(\("[^"]+"' | head -1 | tr -d '(\"')
    port=${local##*:}
    key=$pid
    if [ "$prog" = docker-proxy ]; then   # 포트마다 docker-proxy PID 가 다르므로 컨테이너 이름으로 묶는다
        c=$(docker ps --format '{{.Names}} {{.Ports}}' 2>/dev/null | grep -E "(:|^)${port}->" | awk '{print $1}' | head -1)
        key="docker:${c:-?}"
    fi
    PROG[$key]=$prog
    case " ${PORTS[$key]} " in *" $proto/$port "*) ;; *) PORTS[$key]="${PORTS[$key]} $proto/$port";; esac
done < <(sudo ss -tulnpH)

for key in "${!PORTS[@]}"; do
    ports=$(echo ${PORTS[$key]} | tr ' ' '\n' | sort -t/ -k2 -n | tr '\n' ' ')
    if [[ $key == docker:* ]]; then
        c=${key#docker:}
        echo "── docker $c   포트: $ports"
        [ "$c" != "?" ] && {
            echo "   이미지   : $(docker inspect -f '{{.Config.Image}}' "$c")"
            echo "   compose  : $(docker inspect -f '{{index .Config.Labels "com.docker.compose.project.working_dir"}}' "$c")"
            echo "   안의 계정: $(docker top "$c" -o user= 2>/dev/null | head -1)"; }
        continue
    fi
    pid=$key; prog=${PROG[$key]}
    echo "$prog" | grep -qE "$SYSTEM" && continue
    exe=$(sudo readlink /proc/$pid/exe 2>/dev/null); user=$(ps -o user= -p "$pid")
    [ "$user" = root ] && echo "$exe" | grep -qE '^(/usr/sbin|/usr/lib|/lib|/sbin)/' && continue
    echo "── $prog (pid $pid, 계정 $user)   포트: $ports"
    echo "   실행 파일: ${exe:-?}"
    echo "   작업 폴더: $(sudo readlink /proc/$pid/cwd 2>/dev/null)"
    echo "   시작 시각: $(ps -o lstart= -p "$pid")"
    echo "   명령     : $(ps -o args= -p "$pid" | cut -c1-200)"
done
