#!/bin/bash

# VERSION 변수 확인
if [ -z "$1" ]; then
  echo "Error: VERSION not provided"
  echo "Usage: $0 <VERSION>"
  exit 1
fi

VERSION=$1
REPO="rnd.kware.co.kr"
IMAGE_NAME="kware-thirdeye"

# Docker 로그인
docker login $REPO

# Docker push 설정
docker push $REPO/$IMAGE_NAME:latest
docker push $REPO/$IMAGE_NAME:$VERSION

# push 성공 여부 확인
if [ $? -eq 0 ]; then
  echo "Docker push applied successfully for $IMAGE_NAME version $VERSION and latest"
else
  echo "Error: Docker push failed"
  exit 1
fi
