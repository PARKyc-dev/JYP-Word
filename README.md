<div align="center">

# JYP-Word

**매일 20개의 영단어를 부담 없이, 꾸준히 학습하려면 어떻게 설계해야 할까?**

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?logo=react&logoColor=black)
![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=white)
![IndexedDB](https://img.shields.io/badge/IndexedDB-Local--First-4B5563)

</div>

---

> JYP-Word는 매일 아침 정해진 분량의 영단어를 예문과 함께 학습하는 습관에서 아이디어를 얻어 만든 개인 영어 단어 학습 프로젝트입니다.
>
> 단어, 뜻, 발음, 예문, 예문 해석을 미리 정제된 데이터로 준비하고, 사용자는 자신의 수준과 관심 분야에 맞는 단어를 매일 20개씩 학습하는 것을 목표로 합니다.

## 이 프로젝트에서 확인하려는 것

```text
데이터 고도화 및 정제
Indexed DB
매일 발생하는 일정 갯수의 이력 처리 방법
```

## 단어 데이터 관리 방향

초기 단어 데이터는 CSV, JSON 등의 파일 형태로 관리하고 서비스에서 사용할 수 있도록 DB에 적재하는 방식을 고려합니다.

```text
data/
└── words.csv
       │
       │ import
       ▼
┌───────────────────────┐
│ Word                  │
│ Meaning               │
│ Example               │
│ Category              │
│ WordCategory          │
└───────────────────────┘
```

## 단어 데이터 자동화 시도와 현재 판단

단어장은 다음 네 가지 데이터를 함께 제공해야 한다.

```text
프로젝트용 단어
+ 한글 뜻
+ 영어 예문
+ 예문 한국어 해석
```

따라서 런타임 조회와 분리된 데이터 제작 배치를 먼저 검증했다. 후보 단어는
CEFR 난이도 데이터셋에서 고르고, 한국어 뜻·발음 후보는 Wiktionary,
영어 예문과 한국어 해석 쌍은 Tatoeba에서 수집하는 방식이다.

```text
후보 단어 CSV
    ├─ Wiktionary → 한글 뜻 후보 / IPA / 오디오 후보
    └─ Tatoeba    → 영어 예문 / 연결된 한국어 해석 후보
             ↓
        CSV 초안 + 출처·라이선스 + 검수 사유
             ↓
            사람 검수
             ↓
        PUBLISHED 데이터만 DB 적재
```

현재 저장소에는 이 흐름을 검증한 스크립트와 100개 후보 샘플이 있다.

| 항목 | 구현·산출물 | 확인된 결과 |
| --- | --- | --- |
| 후보 단어 | `data/word-samples/words-dataset-100.csv` | CEFR 레벨과 카테고리를 포함한 100개 샘플 |
| 뜻·발음·오디오 후보 | `scripts/enrich_wiktionary.py` | 한국어 뜻, IPA, 오디오 후보와 오류 목록 생성 |
| DB 적재 초안 | `scripts/build_word_csvs.py` | `words.csv`, `word_meanings.csv`, `examples.csv`로 분리 생성 |
| 예문 후보 | Tatoeba API | 영어 문장과 직접 연결된 한국어 번역, 문장 ID·기여자·라이선스 기록 |

이 시도에서 중요한 한계도 확인했다.

- 한국어 Wiktionary는 `contractee`와 같은 희귀·전문 용어를 포함해 뜻이 누락될 수 있으므로, 한글 뜻의 신뢰 가능한 단일 기준으로 사용할 수 없다.
- Tatoeba도 모든 단어에 적절한 영-한 예문 쌍을 제공하지 않는다.
- 단어가 문장에 포함되기만 해서는 충분하지 않다. 품사와 뜻이 일치하는지, 문장이 학습용으로 자연스러운지 검수해야 한다.

그래서 현재 자동화의 목표는 **완성본을 무검수로 발행하는 것**이 아니라,
출처와 실패 사유를 갖춘 **검수 가능한 초안 생성**이다. 뜻 또는 예문이
없으면 `NEEDS_REVIEW` 상태와 사유를 기록하고, 둘 다 검수된 단어만
`PUBLISHED`로 전환한다.

```text
DRAFT
  → 자동 수집 결과가 존재함

NEEDS_REVIEW
  → 뜻 누락 / 예문 누락 / 전문 용어·문맥 검수 필요

PUBLISHED
  → 한글 뜻과 예문·해석을 검수 완료함
```

## 사전 API 조사 결과

상용 사전 품질의 한글 뜻을 자동으로 확보하려고 네이버·다음/카카오·구글 등도
검토했다.

- 네이버 사전 API는 종료되어 사용할 수 없다. Papago는 번역 API일 뿐 사전 API가 아니다.
- 다음/카카오 개발자 API는 웹문서·블로그·책 검색 등을 제공하지만 사전 조회 API는 제공하지 않는다.
- Google Cloud Translation은 영한 번역에 사용할 수 있고 월 무료 구간이 있지만, 사전 정의나 예문을 제공하는 API는 아니다.
- Collins, Merriam-Webster, Cambridge는 영영 사전 또는 일부 이중언어 사전 API를 제공한다. 다만 데이터셋 구성, 한국어 지원, 결과를 정적 DB에 저장할 수 있는지의 라이선스는 공급자별 계약·약관을 확인해야 한다.

따라서 무료 자동화의 기본 경로는 계속 검수 초안 방식으로 두고, 사전급 한국어
뜻이 반드시 필요한 범위에는 별도의 상용 데이터 라이선스 또는 번역 API 사용량을
결정한 뒤 보완한다. 외부 API 키와 비밀값은 프론트엔드나 CSV에 저장하지 않는다.


## 프로젝트 구조

초기 구조는 다음과 같은 형태를 고려합니다.

```text
jyp-word/
├── web/        # React 화면 및 IndexedDB 기반 사용자 학습 상태
├── api/        # Spring Boot 단어 조회 및 학습 콘텐츠 제공 API
├── data/       # 검수된 단어 원본 데이터
├── docs/       # 설계 문서
└── memory/     # 작업 상태와 장기 결정 기록
```

백엔드는 Java 21과 Spring Boot를 사용합니다.

프론트엔드는 초기 접근성과 배포 편의성을 우선해 React + TypeScript 기반 웹으로 구성하는 방향을 고려합니다.

웹 서비스로 제공하면 별도의 앱 설치 없이 휴대폰과 PC에서 같은 도메인으로 접근할 수 있으며, 필요하면 PWA 방식으로 확장할 수 있습니다.