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

# Docker 태그 설정
# 로컬 빌드된 kware-thirdeye 이미지를 레포지토리 경로로 태깅
docker tag $IMAGE_NAME:latest $REPO/$IMAGE_NAME:latest
docker tag $IMAGE_NAME:latest $REPO/$IMAGE_NAME:$VERSION

# 태그 설정 성공 여부 확인
if [ $? -eq 0 ]; then
  echo "Docker tags applied successfully for $IMAGE_NAME version $VERSION and latest"
else
  echo "Error: Docker tag failed"
  exit 1
fi
