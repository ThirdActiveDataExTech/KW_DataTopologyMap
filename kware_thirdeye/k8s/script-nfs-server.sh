#!/bin/bash

# 1. 설정 변수
NFS_PATH="/srv/nfs/kubedata"
ALLOWED_NETWORK="*" # 모든 대역 허용

echo "=== NFS Server Setup Start ==="

# 2. 패키지 설치
sudo apt update
sudo apt install -y nfs-kernel-server

# 3. 디렉토리 생성 및 권한 설정
sudo mkdir -p $NFS_PATH
sudo chown nobody:nogroup $NFS_PATH
sudo chmod 777 $NFS_PATH

# 4. /etc/exports 설정
if ! grep -q "$NFS_PATH" /etc/exports; then
    echo "$NFS_PATH $ALLOWED_NETWORK(rw,sync,no_subtree_check,no_root_squash)" | sudo tee -a /etc/exports
fi

# 5. 서비스 재시작
sudo exportfs -a
sudo systemctl restart nfs-kernel-server
sudo systemctl enable nfs-kernel-server

echo "=== NFS Server Setup Complete ==="
