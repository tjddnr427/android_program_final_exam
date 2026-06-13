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

---

## 2. 세션 시작 시 필수 절차 (매번)

```
1. harness_agent/claude-progress.md 읽기 → 마지막 세션 상태 확인
2. harness_agent/feature_list.json 읽기 → 미완료 기능 확인
3. harness_agent/PDR.md 읽기 → 스펙 재확인
4. 미완료 기능 정확히 하나만 선택
5. 구현 → 검증 → 기록
```

---

## 3. 코드 스타일 제약 (절대 준수)

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

## 4. 기능 선택 규칙

- `feature_list.json`에서 `"status": "pending"` 인 항목 중 **의존성이 해결된 것** 하나만 선택
- 한 세션에 두 개 이상 건드리지 않는다
- 선택 즉시 `"status": "in_progress"` 로 변경

---

## 5. 검증 기준 (완료 선언 전 필수)

기능이 완료되었다고 선언하려면 아래를 모두 만족해야 한다:

- [ ] Android Studio에서 빌드 오류 없음 (`./gradlew assembleDebug`)
- [ ] 해당 화면이 PDR 스펙과 일치함
- [ ] reference_folder 스타일 범위를 벗어나지 않음
- [ ] `feature_list.json` 상태를 `"done"`으로 업데이트함
- [ ] `claude-progress.md`에 세션 기록 추가함

---

## 6. 세션 종료 시 필수 절차

```
1. feature_list.json 상태 업데이트
2. claude-progress.md에 완료 내용 기록
3. git add → git commit → git push
```

---

## 7. 파일 위치 규칙

| 생성 파일 유형 | 저장 위치 |
|---|---|
| 하네스 문서 (md) | `harness_agent/` |
| Kotlin 소스 | `app/src/main/java/com/example/final_exam/` |
| XML 레이아웃 | `app/src/main/res/layout/` |
| Drawable (shape 등) | `app/src/main/res/drawable/` |
| 문자열 리소스 | `app/src/main/res/values/strings.xml` |

---

## 8. 기능 의존성 순서

```
Memo.kt (data class)
  └→ MemoStorage.kt
       ├→ SplashActivity.kt + activity_splash.xml
       ├→ MemoListAdapter.kt + item_memo.xml
       │    └→ MemoListActivity.kt + activity_memo_list.xml
       └→ MemoEditActivity.kt + activity_memo_edit.xml
            └→ AndroidManifest.xml 수정 (3개 Activity 등록)
```
