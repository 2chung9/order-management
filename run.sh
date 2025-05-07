#!/bin/bash
echo "=== Order Management System 자동 실행 ==="

# 1. Git 저장소 클론
if [ ! -d "order-management" ]; then
  echo ">> 저장소 클론 중..."
  git clone https://github.com/2chung9/order-management || { echo "Git 클론 실패!"; exit 1; }
else
  echo ">> 기존 프로젝트가 이미 존재합니다. 클론을 생략합니다."
fi

cd order-management || { echo "디렉토리 이동 실패!"; exit 1; }

# 2. 프로젝트 실행
echo ">> 프로젝트 실행..."
./gradlew bootRun

