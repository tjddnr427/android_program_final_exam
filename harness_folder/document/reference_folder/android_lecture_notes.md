# 안드로이드 프로그래밍 강의 정리

---

# 1. ConstraintLayout (07-5)

## 개요
- **ConstraintLayout**: 뷰를 계층 구조로 배치하는 레이아웃
- 뷰 간 제약(Constraint)을 설정하여 상대적 위치를 지정

## 디자인 편집기 구성 요소
1. **팔레트(Palette)** – 사용 가능한 뷰 목록
2. **컴포넌트 트리(Component Tree)** – 뷰 계층 구조 표시
3. **툴바(Toolbar)** – 편집 도구 모음
4. **디자인 편집기(Design editor)** – 레이아웃 시각적 편집
5. **속성(Attributes)** – 선택된 뷰의 속성 설정
6. **보기 모드(View mode)** – 디자인/청사진/코드 뷰 전환

## Constraint 속성 (View의 상대적 위치)

```xml
<!-- 같은 방향 정렬 -->
app:layout_constraintTop_toTopOf="@id/view"        <!-- view의 상단과 현재 view의 상단을 맞춤 -->
app:layout_constraintBottom_toBottomOf="@id/view"  <!-- view의 하단과 현재 view의 하단을 맞춤 -->
app:layout_constraintLeft_toLeftOf="@id/view"      <!-- view의 왼쪽과 현재 view의 왼쪽을 맞춤 -->
app:layout_constraintStart_toStartOf="@id/view"    <!-- view의 시작과 현재 view의 시작을 맞춤 -->
app:layout_constraintRight_toRightOf="@id/view"    <!-- view의 오른쪽과 현재 view의 오른쪽을 맞춤 -->
app:layout_constraintEnd_toEndOf="@id/view"        <!-- view의 끝과 현재 view의 끝을 맞춤 -->

<!-- 반대 방향 정렬 (옆에 붙이기) -->
app:layout_constraintLeft_toRightOf="@id/view"     <!-- view의 오른쪽에 현재 view의 왼쪽을 붙임 -->
app:layout_constraintStart_toEndOf="@id/view"      <!-- view의 끝에 현재 view의 시작을 붙임 -->
app:layout_constraintRight_toLeftOf="@id/view"     <!-- view의 왼쪽에 현재 view의 오른쪽을 붙임 -->
app:layout_constraintEnd_toStartOf="@id/view"      <!-- view의 시작에 현재 view의 끝을 붙임 -->

<!-- 텍스트 기준선 정렬 -->
app:layout_constraintBaseLine_toBaseLineOf="@id/view"  <!-- 기본라인끼리 맞춤 -->
app:layout_constraintBaseLine_toTopOf="@id/view"       <!-- view의 상단과 현재 view의 기본라인을 맞춤 -->
app:layout_constraintBaseLine_toBottomOf="@id/view"    <!-- view의 하단과 현재 view의 기본라인을 맞춤 -->
```

## 기본 예제: 좌상단 배치

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:layout_width="40dp"
        android:layout_height="40dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

## ImageView + TextView 예제

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="157dp"
        android:layout_height="159dp"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@drawable/dog" />

    <!-- imageView의 오른쪽에 붙이고, imageView의 상단에 맞춤 -->
    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="30dp"
        app:layout_constraintStart_toEndOf="@+id/imageView"
        app:layout_constraintTop_toTopOf="@+id/imageView" />

    <!-- imageView의 오른쪽에 붙이고, imageView의 하단에 맞춤 -->
    <TextView
        android:id="@+id/textView2"
        android:layout_width="wrap_content"
        android:layout_height="32dp"
        android:layout_marginStart="30dp"
        android:text="How Cute she is^^"
        android:textColor="#673AB7"
        android:textSize="24sp"
        app:layout_constraintBottom_toBottomOf="@+id/imageView"
        app:layout_constraintStart_toEndOf="@+id/imageView" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## 가운데 정렬 (Chain)

양쪽에 같은 방향으로 제약을 걸면 뷰가 가운데에 배치됨.

```xml
<!-- button1: 왼쪽 끝 ~ button2의 왼쪽 -->
<Button
    android:id="@+id/button1"
    android:layout_width="40dp"
    android:layout_height="40dp"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toLeftOf="@+id/button2"/>

<!-- button2: button1의 오른쪽 ~ 오른쪽 끝 -->
<Button
    android:id="@+id/button2"
    android:layout_width="40dp"
    android:layout_height="40dp"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintLeft_toRightOf="@+id/button1"/>
```

## chainStyle

Chain의 첫 번째 뷰에 `layout_constraintHorizontal_chainStyle`을 설정.

| 값 | 동작 |
|---|---|
| `spread` (기본값) | 뷰들을 균등 간격으로 배치 |
| `spread_inside` | 양 끝 뷰는 가장자리에 붙이고 나머지를 균등 배치 |
| `packed` | 뷰들을 한데 모아 중앙에 배치 |

### spread_inside 예제

```xml
<Button
    android:id="@+id/button1"
    ...
    app:layout_constraintHorizontal_chainStyle="spread_inside"
    app:layout_constraintRight_toLeftOf="@+id/button2"/>
```

### packed 예제

```xml
<Button
    android:id="@+id/button1"
    ...
    app:layout_constraintHorizontal_chainStyle="packed"
    app:layout_constraintRight_toLeftOf="@+id/button2"/>
```

## 0dp 활용 (남는 공간 채우기)

`layout_width="0dp"` + 양쪽 제약 → 남는 공간을 자동으로 채움 (LinearLayout의 `weight="1"`과 동일한 효과)

```xml
<!-- LinearLayout 방식: layout_weight 사용 -->
<LinearLayout
    android:id="@+id/linearLayout"
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:orientation="horizontal"
    app:layout_constraintTop_toTopOf="parent">

    <View android:id="@+id/view1"
        android:layout_width="100dp"
        android:layout_height="match_parent"
        android:background="#aaaaaa" />

    <View android:id="@+id/view2"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"          <!-- 가중치로 남는 공간 채움 -->
        android:background="#8844AA" />

    <View android:id="@+id/view3"
        android:layout_width="100dp"
        android:layout_height="match_parent"
        android:background="#AAFF00" />
</LinearLayout>

<!-- ConstraintLayout 방식: 0dp + 좌우 제약 -->
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="100dp">

    <View android:id="@+id/view1"
        android:layout_width="100dp"
        android:layout_height="match_parent"
        android:background="#aaaaaa"
        app:layout_constraintStart_toStartOf="parent" />

    <View android:id="@+id/view2"
        android:layout_width="0dp"          <!-- 0dp + 양쪽 제약 = 남는 공간 채움 -->
        android:layout_height="match_parent"
        android:background="#8844AA"
        app:layout_constraintStart_toEndOf="@id/view1"
        app:layout_constraintEnd_toStartOf="@id/view3" />

    <View android:id="@+id/view3"
        android:layout_width="100dp"
        android:layout_height="match_parent"
        android:background="#AAFF00"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## Font Baseline 맞추기

크기가 다른 텍스트뷰의 글자 기준선(baseline)을 맞출 때 사용.

```xml
<TextView
    android:id="@+id/tv1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="text1"
    android:textSize="30sp"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toLeftOf="@+id/tv2"/>

<TextView
    android:id="@+id/tv2"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="text2"
    app:layout_constraintBaseline_toBaselineOf="@+id/tv1"   <!-- tv1과 baseline 맞춤 -->
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintLeft_toRightOf="@+id/tv1"/>
```

---

# 2. 제트팩 라이브러리 (11주차)

## 11-1 제트팩과 androidx 소개

### 플랫폼 API
- ART(Android Runtime)에서 제공하는 안드로이드 기본 라이브러리
- `자바 API 프레임워크` 안에 Activity, Location, Package, Notification, Resource, Telephony, Window 등을 포함

### 제트팩(Jetpack)
- 구글에서 안드로이드 앱 개발용으로 제공하는 **다양한 라이브러리 모음**
- **`androidx`로 시작하는 패키지명** 사용
- 주요 역할:
  - 앱 개발에 필요한 권장 아키텍처 제공
  - **API 레벨의 호환성 문제 해결**
  - 플랫폼 API에서 제공하지 않는 다양한 기능 제공

### 주요 androidx 라이브러리 (화면 구성)

| 라이브러리 | 역할 |
|---|---|
| `androidx.appcompat` | API 레벨 호환성 해결 |
| `androidx.recyclerview` | 목록 화면 구성 |
| `androidx.viewpager2` | 스와이프로 넘기는 화면 구성 |
| `androidx.fragment` | 액티비티처럼 동작하는 뷰 제공 |
| `androidx.drawerlayout` | 서랍처럼 열리는 화면 구성 |

---

## 11-4 리사이클러 뷰 (RecyclerView)

### 개요
목록 화면을 효율적으로 구성하는 뷰. 스크롤 시 뷰를 재활용(recycle)하여 성능 최적화.

### 구성 요소

| 구성 요소 | 필수 여부 | 역할 |
|---|---|---|
| **ViewHolder** | 필수 | 항목에 필요한 뷰 객체를 보관 |
| **Adapter** | 필수 | 항목을 구성 |
| **LayoutManager** | 필수 | 항목을 배치 |
| **ItemDecoration** | 옵션 | 항목을 꾸밈 (구분선 등) |

### ViewHolder 패턴
- 기존 방식: 스크롤마다 `inflate` + `findViewById` → 성능 저하
- ViewHolder: **뷰 객체를 미리 보관**해두고 재활용 → `findViewById` 호출 감소 → 성능 향상

### 1단계: ViewHolder 준비

```kotlin
class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)
```

### 2단계: Adapter 준비

Adapter는 3개의 메서드를 반드시 구현해야 함.

| 메서드 | 호출 시점 | 역할 |
|---|---|---|
| `getItemCount()` | 자동 | 항목 개수 반환 |
| `onCreateViewHolder()` | 자동 | 새 ViewHolder 생성 |
| `onBindViewHolder()` | 자동 | ViewHolder에 데이터 바인딩 |

```kotlin
class MyAdapter(val datas: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
```

### GroceryApp 전체 구현 예제

#### 구조
- `ItemModel` 데이터 클래스
- `MyAdapter` (RecyclerViewAdapter)
  - `MyViewHolder` inner class
  - `onCreateViewHolder()`
  - `getItemCount()` & `onBindViewHolder()`
- `MainActivity.kt`

#### ItemModel

```kotlin
data class ItenModel(val name: String, val desc: String, val img: Int)
```

#### MyAdapter 전체 코드

```kotlin
class MyAdapter(val itemList: ArrayList<ItenModel>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemTitle.setText(itemList[position].name)
        holder.itemDesc.setText(itemList[position].desc)
        holder.itemImg.setImageResource(itemList[position].img)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImg: ImageView
        var itemTitle: TextView
        var itemDesc: TextView

        init {
            itemImg = itemView.findViewById(R.id.imageview)
            itemTitle = itemView.findViewById(R.id.title_txt)
            itemDesc = itemView.findViewById(R.id.description_text)
        }
    }
}
```

#### MainActivity.kt

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var groceryItems: ArrayList<ItenModel> = ArrayList()
        val v1 = ItenModel("Fruits", "Fresh Fruits from the Garden", R.drawable.fruit)
        val v2 = ItenModel("Vegetables", "Delicious Vegetables", R.drawable.vegitables)
        val v3 = ItenModel("Bakery", "Bread, Wheat and Beans", R.drawable.bread)
        val v4 = ItenModel("Beverage", "Juice, Tea, Coffee and Soda", R.drawable.beverage)
        val v5 = ItenModel("Milk", "Milk, Shakes and Yogurt", R.drawable.milk)
        val v6 = ItenModel("Snacks", "Pop Corn, Donut and Drinks", R.drawable.popcorn)

        groceryItems.add(v1)
        groceryItems.add(v2)
        groceryItems.add(v3)
        groceryItems.add(v4)
        groceryItems.add(v5)
        groceryItems.add(v6)

        val adapter = MyAdapter(groceryItems)
        recyclerView.adapter = adapter
    }
}
```

#### activity_main.xml (RecyclerView 배치)

```xml
<androidx.constraintlayout.widget.ConstraintLayout ...>

    <ImageView
        android:id="@+id/image_header"
        android:layout_width="0dp"
        android:layout_height="230dp"
        android:src="@drawable/header"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginTop="24dp"
        android:background="#283339"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/image_header" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### item_layout.xml (각 항목 레이아웃)

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/imageview"
        android:layout_width="120dp"
        android:layout_height="120dp"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent" />

    <TextView
        android:id="@+id/title_txt"
        android:textColor="@color/white"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginTop="16dp"
        android:text="title"
        android:textSize="32sp"
        app:layout_constraintStart_toEndOf="@+id/imageview"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/description_text"
        android:textColor="@color/white"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:text="Your description here"
        android:textSize="16sp"
        app:layout_constraintStart_toStartOf="@+id/title_txt"
        app:layout_constraintTop_toBottomOf="@+id/title_txt" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### CardView
- View들을 그룹으로 묶어서 관리하는 View
- 그림자 효과로 공중에 떠있는 듯한 느낌

| 속성 | 설명 |
|---|---|
| `card_view:cardCornerRadius` | 레이아웃에서 모서리 반경 설정 |
| `CardView.setRadius` | 코드에서 모서리 반경 설정 |
| `card_view:cardBackgroundColor` | 카드뷰 배경색 설정 |
| `card_view:cardElevation` | 그림자 크기 (값이 클수록 진함) |

---

# 3. 제트팩 라이브러리 (13주차)

## 액티비티 생명주기 (Activity Lifecycle)

### 생명주기 흐름

**앱 실행 시:** `onCreate()` → `onStart()` → `onResume()` → **메인 액티비티 실행**

**앱 종료 시:** `onPause()` → `onStop()` → `onDestroy()` → **종료**

**다른 액티비티 이동 시:** `onPause()` → `onStop()` → (다른 액티비티 사용) → `onRestart()` → `onStart()` → `onResume()` → **메인 액티비티로 복귀**

> ⚠️ **이미지 보존**: 액티비티 생명주기 다이어그램은 흐름도이므로 원본 슬라이드 참고

---

## 11-3 프래그먼트 (Fragment)

### 개요
- 프래그먼트가 일반 View와 다른 점: **액티비티처럼 동작**
- 액티비티에 구현되는 모든 내용을 프래그먼트 클래스에도 작성 가능
- 하나의 액티비티 안에 여러 프래그먼트를 배치하여 화면 분할 가능 (태블릿 UI 등)

### 프래그먼트 구현

- `Fragment` 클래스를 상속
- **최소한으로 구현해야 하는 함수**: `onCreateView()`
- `onCreateView()`가 반환한 `View` 객체가 화면에 출력

```kotlin
import androidx.fragment.app.Fragment

class OneFragment : Fragment() {
    lateinit var binding: FragmentOneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }
}
```

### XML에서 프래그먼트 출력

`FragmentContainerView` 사용:

```xml
<androidx.fragment.app.FragmentContainerView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/fragment_content"
    android:name="com.example.test_11.OneFragment">
</androidx.fragment.app.FragmentContainerView>
```

### 코드에서 프래그먼트 동적 제어

`FragmentManager` → `FragmentTransaction` 사용:

| 메서드 | 역할 |
|---|---|
| `add(containerViewId, fragment)` | 새 프래그먼트를 화면에 추가 |
| `replace(containerViewId, fragment)` | 기존 프래그먼트를 대체 |
| `remove(fragment)` | 프래그먼트 제거 |
| `commit()` | 변경사항을 화면에 적용 |

```kotlin
val fragmentManager: FragmentManager = supportFragmentManager
val transaction: FragmentTransaction = fragmentManager.beginTransaction()
val fragment = OneFragment()
transaction.add(R.id.fragment_content, fragment)
transaction.commit()
```

### 프래그먼트 생명주기

액티비티 생명주기와 동일한 함수(`onCreate`, `onStart`, `onResume`, `onPause`, `onStop`, `onDestroy`)를 가짐.

**단계:** 초기화(initialized) → 생성(created) → 시작(started) → 재개(resumed) → 소멸(destroyed)

> ⚠️ **이미지 보존**: 프래그먼트 생명주기 다이어그램은 원본 슬라이드 참고

### 백 스택 (Back Stack)

프래그먼트가 화면에서 사라질 때 완전히 제거하지 않고 저장해두는 기능.

| 백 스택 사용 여부 | 동작 |
|---|---|
| 사용 안 함 | 프래그먼트 교체 시 `onDetach()`까지 호출되어 완전 제거 |
| 사용 | `onDestroyView()`까지만 호출, 나중에 재사용 가능 |

```kotlin
transaction.addToBackStack(null)  // 백 스택 사용 설정
```

---

## 11-5 뷰 페이저2 (ViewPager2)

### 개요
- 2019년에 기존 ViewPager를 대체하여 출시
- 항목이 순서대로 나열되고 **한 화면에 하나의 항목**이 스와이프로 넘어가는 방식
- 어댑터 종류: `RecyclerView.Adapter` 또는 `FragmentStateAdapter`

### 방법 1: RecyclerView 어댑터 이용

```kotlin
class MyPagerViewHolder(val binding: ItemPagerBinding) :
    RecyclerView.ViewHolder(binding.root)

class MyPagerAdapter(val datas: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyPagerViewHolder(
            ItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyPagerViewHolder).binding
        binding.itemPagerTextView.text = datas[position]
        when (position % 3) {
            0 -> binding.itemPagerTextView.setBackgroundColor(Color.RED)
            1 -> binding.itemPagerTextView.setBackgroundColor(Color.BLUE)
            2 -> binding.itemPagerTextView.setBackgroundColor(Color.GREEN)
        }
    }
}
```

### 방법 2: FragmentStateAdapter 이용

```kotlin
class MyFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    val fragments: List<Fragment>

    init {
        fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
    }

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}

// 어댑터 적용
binding.viewpager.adapter = MyPagerAdapter(datas)
```

### ViewPager2 실습: FragmentStateAdapter 방식

#### 1. 새 Fragment 생성
Android Studio에서 `New > Fragment > Fragment (Blank)`로 생성

#### 2. activity_main.xml

```xml
<androidx.constraintlayout.widget.ConstraintLayout ...>

    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/viewPager2"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### 3. Fragment XML (예: fragment_one.xml)

```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#DFFF00"
    tools:context=".FragmentOne">

    <TextView
        android:textSize="32dp"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="Fragment #1" />
</FrameLayout>
```

#### 4. MyPagerAdapter (FragmentStateAdapter)

```kotlin
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter(fm: FragmentManager, lc: Lifecycle)
    : FragmentStateAdapter(fm, lc) {

    var fragmentList: ArrayList<Fragment> = ArrayList()

    fun addFragmentToList(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}
```

#### 5. Fragment 클래스

```kotlin
class FragmentOne : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }
}

class FragmentTwo : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_two, container, false)
    }
}
```

#### 6. MainActivity 구현

```kotlin
class MainActivity : AppCompatActivity() {
    lateinit var viewPager2: ViewPager2
    lateinit var myAdapter: MyPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 뷰페이저 초기화
        viewPager2 = findViewById(R.id.viewPager2)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 2. 어댑터에 프래그먼트 추가
        myAdapter = MyPagerAdapter(supportFragmentManager, lifecycle)
        myAdapter.addFragmentToList(FragmentOne())
        myAdapter.addFragmentToList(FragmentTwo())
        myAdapter.addFragmentToList(FragmentThree())

        // 3. 어댑터 연결
        viewPager2.adapter = myAdapter
    }
}
```

---

## 메뉴와 대화상자

### 메뉴 종류

| 종류 | 설명 |
|---|---|
| **옵션 메뉴** | 화면 오른쪽 위 메뉴 아이콘(⋮)을 눌렀을 때 나오는 메뉴 |
| **컨텍스트 메뉴** | 위젯을 롱클릭하면 나오는 메뉴, 화면 중앙에 출력 |

### XML을 이용한 옵션 메뉴 구현 순서

1. 메뉴 XML 파일 작성 (`res/menu/` 폴더)
2. `onCreateOptionsMenu()` 오버라이딩 → 메뉴 파일 등록
3. `onOptionsItemSelected()` 오버라이딩 → 메뉴 선택 동작 정의

#### 1. 메뉴 XML 형식 (`res/menu/mymenu.xml`)

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/itemRed"
        android:title="배경색 (빨강)">
    </item>
    <item
        android:id="@+id/itemGreen"
        android:title="배경색(초록)">
    </item>
    <item
        android:id="@+id/itemBlue"
        android:title="배경색(파랑)">
    </item>
</menu>
```

#### 2. onCreateOptionsMenu()

```kotlin
override fun onCreateOptionsMenu(menu: Menu?) : Boolean {
    super.onCreateOptionsMenu(menu)
    var mInflater = menuInflater
    mInflater.inflate(R.menu.mymenu, menu)  // 메뉴 XML 아이디
    return true
}
```

#### 3. onOptionsItemSelected()

```kotlin
override fun onOptionsItemSelected(item: MenuItem) : Boolean {
    when (item.itemId) {
        R.id.itemRed -> {
            baseLayout.setBackgroundColor(Color.RED)
            return true
        }
        R.id.itemGreen -> {
            baseLayout.setBackgroundColor(Color.GREEN)
            return true
        }
        R.id.itemBlue -> {
            baseLayout.setBackgroundColor(Color.BLUE)
            return true
        }
    }
    return false
}
```

#### 4. activity_main.xml (Toolbar 포함)

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="409dp"
        android:layout_height="wrap_content"
        android:background="@color/design_default_color_secondary"
        android:minHeight="?attr/actionBarSize"
        android:theme="?attr/actionBarTheme" />

</LinearLayout>
```

---

## 시험 대비 핵심 요약

### ConstraintLayout 핵심
- `0dp` = 제약으로 크기가 결정됨 (MATCH_CONSTRAINT)
- `chainStyle`: `spread` / `spread_inside` / `packed`
- `baseline` 정렬로 다른 크기 텍스트 정렬 가능

### RecyclerView 핵심
- **반드시 구현할 3가지**: `getItemCount()`, `onCreateViewHolder()`, `onBindViewHolder()`
- ViewHolder 패턴: `indexOf()` 감소로 성능 향상
- LayoutManager: `LinearLayoutManager(this, VERTICAL/HORIZONTAL, false)`

### Fragment 핵심
- **최소 구현 함수**: `onCreateView()`
- `FragmentTransaction`: `add()`, `replace()`, `remove()`, `commit()`
- 백 스택: `addToBackStack(null)`

### ViewPager2 핵심
- 어댑터 2종류: `RecyclerView.Adapter` 또는 `FragmentStateAdapter`
- FragmentStateAdapter: `createFragment(position)`, `getItemCount()` 구현
- MainActivity에서 `supportFragmentManager`와 `lifecycle` 전달

### 액티비티 생명주기 핵심
- 실행: `onCreate` → `onStart` → `onResume`
- 종료: `onPause` → `onStop` → `onDestroy`
- 다른 액티비티로 이동 후 복귀: `onRestart` → `onStart` → `onResume`
