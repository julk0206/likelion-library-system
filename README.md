# likelion-library-system

Hello!

## Eclipse - Workspace

> `likelion-library-system`

## open Project

> `library-system`

## web.xml 설정

- Server 를 로컬에서 설정했기 때문에 따로 붙이셔야 합니다~

## commit 작성 가이드

- 주요 커밋 타입과 의미

| 타입           | 의미                                                      | 추천 이모티콘              |
| -------------- | --------------------------------------------------------- | -------------------------- |
| **`feat`**     | 새로운 기능 추가 (Feature)                                | ✨ `:sparkles:`            |
| **`fix`**      | 버그 수정 (Bug Fix)                                       | 🐛 `:bug:`                 |
| **`chore`**    | 빌드 업무, 패키지 매니저 설정, 단순 잡무 (코드 변경 없음) | 🚚 `:truck:` / ⚙️          |
| **`docs`**     | 문서 수정 (README, 주석 등)                               | 📝 `:memo:`                |
| **`style`**    | 코드 포맷팅, 세미콜론 누락 등 (코드 로직 변경 없음)       | 💄 `:lipstick:`            |
| **`refactor`** | 코드 리팩토링 (기능은 그대로, 가독성/구조 개선)           | ♻️ `:recycle:`             |
| **`test`**     | 테스트 코드 추가 및 수정                                  | ✅ `:white_check_mark:`    |
| **`perf`**     | 성능 개선 (Performance)                                   | ⚡ `:zap:`                 |
| **`ci`**       | CI 설정 수정 (GitHub Actions, Jenkins 등)                 | 👷 `:construction_worker:` |
| **`revert`**   | 이전 커밋 되돌리기                                        | ⏪ `:rewind:`              |

---

💡 작성 팁 (예시)

보통 `타입: 설명` 또는 `이모티콘 타입: 설명` 형태로 작성합니다.

- **예시 1 (기본):** `feat: 소셜 로그인 기능 추가`
- **예시 2 (이모티콘 활용):** `✨ feat: 카카오톡 로그인 연동`
- **예시 3 (상세):** `🐛 fix: 메인 페이지 이미지 로딩 에러 수정`

🔍 더 알아두면 좋은 점

- **`build`**: 빌드 시스템이나 외부 의존성 관련 수정 시 사용하기도 합니다.
- **`rename`**: 단순히 파일이나 폴더명을 바꿀 때 따로 쓰기도 하지만 보통 `chore`나 `style`에 포함하기도 합니다.
