# 나만의 메모장 — 세션 시작 지침

## 세션 시작 시 반드시 이 순서대로 실행

1. `harness_agent/claude-progress.md` 읽기 → 마지막 세션 상태 확인
2. `harness_agent/feature_list.json` 읽기 → 미완료(pending) 기능 확인
3. `harness_agent/AGENTS.md` 읽기 → 코드 스타일 제약 및 규칙 재확인
4. `harness_agent/PDR.md` 읽기 → 구현할 화면 스펙 확인
5. 의존성이 해결된 pending 기능 **하나만** 선택하여 구현 시작

## 절대 규칙

- `harness_folder/document/reference_folder/` 코드 스타일 범위를 절대 벗어나지 않는다
- 클로드가 생성하는 md 파일은 `harness_agent/` 폴더에 저장한다
- 기능 완료 후 반드시 `feature_list.json` 상태 업데이트 → `claude-progress.md` 기록 → git push
