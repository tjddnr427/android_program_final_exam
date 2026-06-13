# 나만의 메모장 — 세션 시작 지침 (멀티 에이전트)

## 세션 시작 시 반드시 이 순서대로 실행

1. `harness_agent/claude-progress.md` 읽기 → 현재 Wave 및 마지막 상태 확인
2. `harness_agent/feature_list.json` 읽기 → `"status": "pending"` 이고 `"agent": null` 인 항목 확인
3. `harness_agent/AGENTS.md` 읽기 → 코드 스타일 제약 및 Wave 규칙 재확인
4. `harness_agent/PDR.md` 읽기 → 담당 기능 스펙 확인
5. 현재 Wave에서 `"agent": null` 인 기능 **하나만** 선택
6. `feature_list.json`에서 해당 기능의 `"agent"`를 내 이름으로, `"status"`를 `"in_progress"`로 변경 후 **즉시 git push** (클레임)
7. 구현 시작

## 절대 규칙

- `harness_folder/document/reference_folder/` 코드 스타일 범위를 절대 벗어나지 않는다
- 클로드가 생성하는 md 파일은 `harness_agent/` 폴더에 저장한다
- `"agent"` 필드가 이미 채워진 기능은 건드리지 않는다 (다른 에이전트 작업 중)
- 현재 Wave의 모든 기능이 `"done"` 이 되어야 다음 Wave 진입 가능
- 기능 완료 후: `feature_list.json` 업데이트 → `claude-progress.md` 기록 → git push

## Wave 현황

| Wave | 기능 | 비고 |
|---|---|---|
| 1 | F01, F03, F04, F07, F08, F14 | 동시 시작 가능 |
| 2 | F02, F05, F06 | Wave 1 완료 후 |
| 3 | F09, F10 | Wave 2 완료 후 |
| 4 | F11, F12 | Wave 3 완료 후 |
| 5 | F13 | Wave 4 완료 후 |
