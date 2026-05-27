#!/bin/bash

echo "=== NFS Client Setup Start ==="

# 1. 패키지 설치
sudo apt update
sudo apt install -y nfs-common

# 2. 필수 서비스 활성화
sudo systemctl start rpcbind
sudo systemctl enable rpcbind

echo "=== NFS Client Setup Complete ==="
