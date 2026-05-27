#!/bin/bash

# NFS Provisioner Install Script for Kubernetes
# 이 스크립트는 Helm을 사용하여 nfs-subdir-external-provisioner를 설치합니다.

set -e

echo "=== Kubernetes NFS Provisioner Installation Start ==="

# 1. Helm 설치 확인 및 설치
if ! command -v helm &> /dev/null; then
    echo "Helm is not installed. Installing Helm..."
    curl https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 | bash
else
    echo "Helm is already installed."
fi

# 2. 사용자 입력 (NFS 서버 IP 및 경로)
# 환경에 맞게 기본값을 수정했습니다. (Enter를 치면 기본값이 사용됩니다.)
read -p "Enter NFS Server IP (default: 172.30.1.1): " NFS_SERVER_IP
NFS_SERVER_IP=${NFS_SERVER_IP:-172.30.1.1}

read -p "Enter NFS Export Path (default: /home/nfs_data/kube): " NFS_PATH
NFS_PATH=${NFS_PATH:-/home/nfs_data/kube}

# 3. Helm 레포지토리 추가 및 업데이트
echo "Adding Helm repository..."
helm repo add nfs-subdir-external-provisioner https://kubernetes-sigs.github.io/nfs-subdir-external-provisioner/
helm repo update

# 4. NFS Provisioner 설치
# thirdeye 네임스페이스에 설치하거나 기본 네임스페이스에 설치할 수 있습니다.
echo "Installing/Updating NFS Subdir External Provisioner..."
helm upgrade --install nfs-client nfs-subdir-external-provisioner/nfs-subdir-external-provisioner \
    --set nfs.server=$NFS_SERVER_IP \
    --set nfs.path=$NFS_PATH \
    --set storageClass.name=thirdeye-storage-class \
    --set storageClass.reclaimPolicy=Retain \
    --set storageClass.archiveOnDelete=true

echo ""
echo "=== Installation Complete ==="
echo "You can now use 'storageClassName: nfs-client' in your PVCs."
echo "Check status: kubectl get pods -l app=nfs-subdir-external-provisioner"
