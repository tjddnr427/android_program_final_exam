# AGENTS.md — 나만의 메모장 하네스 운영 설명서

> "모델은 똑똑하다. 하네스가 신뢰할 수 있게 만든다."

---

## 1. 프로젝트 컨텍스트

| 항목 | 내용 |
|---|---|
| 앱 이름 | 나만의 메모장 |
| 패키지 | com.example.final_exam |
| 언어 | Kotlin (Android) |
| 스펙 문서 | `harness_agent/PDR.md` |
| 참고 자료 | `harness_folder/document/reference_folder/` |
| 에이전트 방식 | **멀티 에이전트 (Wave 병렬 실행)** |

---

## 2. 멀티 에이전트 Wave 구조

```
Wave 1 (동시 실행 가능)
  ├─ F01  Memo.kt
  ├─ F03  shape_rounded_title.xml
  ├─ F04  shape_memo_card.xml
  ├─ F07  activity_memo_list.xml
  ├─ F08  activity_memo_edit.xml
  └─ F14  strings.xml

Wave 2 (Wave 1 완료 후)
  ├─ F02  MemoStorage.kt
  ├─ F05  activity_splash.xml
  └─ F06  item_memo.xml

Wave 3 (Wave 2 완료 후)
  ├─ F09  SplashActivity.kt
  └─ F10  MemoListAdapter.kt

Wave 4 (Wave 3 완료 후)
  ├─ F11  MemoListActivity.kt
  └─ F12  MemoEditActivity.kt

Wave 5 (Wave 4 완료 후)
  └─ F13  AndroidManifest.xml 수정
```

---

## 3. 세션 시작 시 필수 절차 (매번)

```
1. harness_agent/claude-progress.md 읽기 → 현재 Wave 및 마지막 상태 확인
2. harness_agent/feature_list.json 읽기 → pending이고 agent가 null인 항목 확인
3. harness_agent/PDR.md 읽기 → 담당 기능 스펙 재확인
4. feature_list.json에서 내 기능의 "agent" 필드를 내 이름으로 설정 (클레임)
5. "status"를 "in_progress"로 변경 후 즉시 git push (충돌 방지)
6. 구현 → 검증 → 기록
```

---

## 4. 티켓 클레임 규칙 (충돌 방지)

- 작업 시작 전 `feature_list.json`의 해당 기능 `"agent"` 필드를 **자신의 이름**으로 설정
- `"status": "in_progress"` 로 변경
- **즉시 git commit & push** (다른 에이전트가 같은 티켓 선택하는 것 방지)
- `"agent"` 필드가 이미 채워져 있으면 → 다른 티켓 선택

```json
// 클레임 예시
{
  "id": "F01",
  "status": "in_progress",
  "agent": "agent-A"
}
```

---

## 5. Wave 진입 조건

- 현재 Wave의 모든 기능이 `"status": "done"` 이어야 다음 Wave 시작 가능
- Wave 전환 전 반드시 `claude-progress.md`에 Wave 완료 기록

---

## 6. 코드 스타일 제약 (절대 준수)

`harness_folder/document/reference_folder/` 스타일 범위를 절대 벗어나지 않는다.

| 허용 | 금지 |
|---|---|
| `AppCompatActivity` 상속 | `Activity` 직접 상속 |
| `findViewById<T>()` | ViewBinding, DataBinding |
| XML 레이아웃 + ConstraintLayout | Jetpack Compose |
| `lateinit var` 뷰 선언 | `by lazy` |
| `BaseAdapter` inner class 어댑터 | RecyclerView.Adapter |
| `putExtra` / `getStringExtra` 등 | Navigation Component |
| `startActivityForResult` / `onActivityResult` | ActivityResultLauncher |
| `SharedPreferences` + `org.json` | Room, Retrofit, Hilt |
| ViewModel, LiveData, Coroutines 사용 금지 | — |

---

## 7. 검증 기준 (완료 선언 전 필수)

- [ ] 해당 파일이 PDR 스펙과 일치함
- [ ] reference_folder 스타일 범위를 벗어나지 않음
- [ ] 의존하는 상위 기능들의 인터페이스와 일치함
- [ ] `feature_list.json` 상태를 `"done"`으로, `"agent"`는 유지
- [ ] `claude-progress.md`에 세션 기록 추가
- [ ] git commit & push 완료

---

## 8. 세션 종료 시 필수 절차

```
1. feature_list.json → "status": "done" 업데이트
2. claude-progress.md → 완료 내용 기록
3. git add → git commit → git push
```

---

## 9. 파일 위치 규칙

| 생성 파일 유형 | 저장 위치 |
|---|---|
| 하네스 문서 (md) | `harness_agent/` |
| Kotlin 소스 | `app/src/main/java/com/example/final_exam/` |
| XML 레이아웃 | `app/src/main/res/layout/` |
| Drawable (shape 등) | `app/src/main/res/drawable/` |
| 문자열 리소스 | `app/src/main/res/values/strings.xml` |
