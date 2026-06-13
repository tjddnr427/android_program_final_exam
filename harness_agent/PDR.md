# PDR — 나만의 메모장 (Android Final Exam)

**작성자:** 2271333 권성욱  
**작성일:** 2026-06-13  
**기반:** 안드로이드프로그래밍 아웃라인.pdf

---

## 1. 앱 개요

| 항목 | 내용 |
|---|---|
| 앱 이름 | 나만의 메모장 |
| 패키지 | com.example.final_exam |
| 최소 SDK | 24 |
| 언어 | Kotlin |
| 저장 방식 | SharedPreferences (JSON 직렬화) |

---

## 2. 화면 구성 (3개 Activity)

### 화면 1 — SplashActivity (시작 화면)
- 앱 실행 시 단 한 번만 표시
- 화면 중앙보다 살짝 위: 앱 이름 "나만의 메모장"을 **둥근 네모(RoundedRectangle)** 안에 표시
- 메모가 **있을 경우**:
  - "이전 메모로 바로가기" 버튼 표시
  - 가장 최신 날짜 메모 카드 (메모장 이름 / 첫 번째 줄 / 날짜) 표시
  - 버튼 또는 카드 클릭 → 해당 메모를 MemoEditActivity로 이동
- 메모가 **없을 경우**:
  - "새 메모 작성" 텍스트 표시 (클릭 시 빈 MemoEditActivity 열기)
- SplashActivity는 뒤로가기 스택에서 제거 (`finish()` 후 MemoListActivity로 이동)

### 화면 2 — MemoListActivity (메모 목록)
- 모든 메모를 **최근 업데이트 날짜 내림차순**으로 정렬
- ListView + 커스텀 Adapter 사용
- 각 항목 표시:
  - 메모장 이름 (없으면 "이름 없음")
  - 메모의 첫 번째 줄
  - 가장 최근 업데이트 날짜
- 항목 클릭 → MemoEditActivity로 이동 (메모 ID 전달)
- 우측 하단 FAB(+) 버튼 → 새 메모 MemoEditActivity 열기

### 화면 3 — MemoEditActivity (메모 편집)
- 상단: 메모 이름 EditText (기본값 "이름 없음", 클릭하여 수정 가능)
- 하단: 메모 내용 EditText (멀티라인, 전체 영역)
- **자동 저장**: `onPause()` 생명주기 메서드에서 저장
- 새 메모는 진입 시 UUID로 ID 생성

---

## 3. 화면 간 네비게이션

```
앱 시작
   ↓
SplashActivity (한 번만)
   ├─ 메모 있음 → 최신 메모 카드/버튼 클릭 → MemoEditActivity
   └─ 메모 없음 → "새 메모 작성" 클릭 → MemoEditActivity
          ↑↓ (자유 이동)
   MemoListActivity  ←→  MemoEditActivity
```

- 화면 2 ↔ 화면 3은 BottomNavigationView 또는 Intent로 자유 이동
- 화면 1은 `finish()` 처리하여 뒤로가기 불가

---

## 4. 데이터 모델

```kotlin
data class Memo(
    val id: String,          // UUID
    var title: String,       // 메모장 이름 (기본: "이름 없음")
    var content: String,     // 메모 내용
    var updatedAt: Long      // 마지막 수정 시각 (ms)
)
```

---

## 5. 저장 방식

- `SharedPreferences` key: `"memos"`
- value: Memo 리스트를 JSON 문자열로 직렬화 (`org.json.JSONArray`)
- 저장 시점: `MemoEditActivity.onPause()`
- 읽기 시점: 각 Activity의 `onCreate()` / `onResume()`

---

## 6. 주요 기술 항목 (수업 참고)

| 기능 | 적용 기술 |
|---|---|
| 화면 이동 | 명시적 Intent + putExtra (메모 ID 전달) |
| 메모 목록 | ListView + BaseAdapter (커스텀 행 레이아웃) |
| 자동 저장 | Activity 생명주기 onPause() |
| 화면 1 표시 | SplashActivity → finish() → MemoListActivity |
| 둥근 네모 | drawable/shape (cornerRadius) |

---

## 7. 파일 구성 (생성 대상)

### Kotlin
- `SplashActivity.kt`
- `MemoListActivity.kt`
- `MemoEditActivity.kt`
- `Memo.kt` (data class)
- `MemoStorage.kt` (SharedPreferences 유틸)
- `MemoListAdapter.kt` (ListView 커스텀 어댑터)

### Layout XML
- `activity_splash.xml`
- `activity_memo_list.xml`
- `activity_memo_edit.xml`
- `item_memo.xml` (ListView 행 레이아웃)

### Drawable
- `shape_rounded_title.xml` (둥근 네모 배경)
- `shape_memo_card.xml` (메모 카드 배경)

### 기타
- `AndroidManifest.xml` 수정 (3개 Activity 등록)
- `strings.xml` 수정
