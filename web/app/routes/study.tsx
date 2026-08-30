import { useRef, useState } from "react";
import { Link } from "react-router";

import type { Route } from "./+types/study";
import { ThemeToggle } from "../theme";

type Word = {
  word: string;
  pronunciation: string;
  meaning: string;
  sentence: string;
  translation: string;
};

const initialWords: Word[] = [
  {
    word: "deliberate",
    pronunciation: "/dɪˈlɪbərət/",
    meaning: "신중한, 의도적인",
    sentence: "She made a deliberate effort to speak more slowly.",
    translation: "그녀는 더 천천히 말하려고 의식적으로 노력했다.",
  },
  {
    word: "maintain",
    pronunciation: "/meɪnˈteɪn/",
    meaning: "유지하다",
    sentence: "It is important to maintain a regular study routine.",
    translation: "규칙적인 학습 습관을 유지하는 것이 중요하다.",
  },
  {
    word: "approach",
    pronunciation: "/əˈproʊtʃ/",
    meaning: "접근법, 다가가다",
    sentence: "Try a different approach when the current one feels difficult.",
    translation: "현재 방법이 어렵게 느껴지면 다른 접근법을 시도해 봐라.",
  },
  {
    word: "consistent",
    pronunciation: "/kənˈsɪstənt/",
    meaning: "꾸준한, 일관된",
    sentence: "Consistent practice creates lasting progress.",
    translation: "꾸준한 연습은 지속되는 발전을 만든다.",
  },
];

export function meta({}: Route.MetaArgs) {
  return [{ title: "오늘의 학습 | JYP Word" }];
}

export default function Study() {
  const [studyWords, setStudyWords] = useState(initialWords);
  const [testWords, setTestWords] = useState<Word[]>([]);
  const [isRevealed, setIsRevealed] = useState(false);
  const [isTransitioning, setIsTransitioning] = useState(false);
  const [drag, setDrag] = useState({ x: 0, y: 0, isDragging: false });
  const pointerStart = useRef<{ x: number; y: number } | null>(null);
  const currentWord = studyWords[0];
  const cardStyle = {
    opacity: 1 - Math.min(0.35, Math.max(Math.abs(drag.x), Math.abs(drag.y)) / 900),
    transform: `translate3d(${drag.x}px, ${drag.y}px, 0) rotate(${drag.x * 0.035}deg)`,
    transition: drag.isDragging ? "none" : "transform 180ms ease, opacity 180ms ease",
  };

  function showNext(addToTest = false) {
    if (!currentWord) return;

    setStudyWords((words) => {
      const [word, ...remainingWords] = words;
      return addToTest || !word ? remainingWords : [...remainingWords, word];
    });
    if (addToTest) setTestWords((words) => [...words, currentWord]);
    setIsRevealed(false);
  }

  function handlePointerDown(event: React.PointerEvent<HTMLElement>) {
    if (isTransitioning) return;
    pointerStart.current = { x: event.clientX, y: event.clientY };
    event.currentTarget.setPointerCapture(event.pointerId);
  }

  function handlePointerMove(event: React.PointerEvent<HTMLElement>) {
    const start = pointerStart.current;
    if (!start || isTransitioning) return;

    setDrag({
      x: event.clientX - start.x,
      y: event.clientY - start.y,
      isDragging: true,
    });
  }

  function handlePointerUp(event: React.PointerEvent<HTMLElement>) {
    const start = pointerStart.current;
    pointerStart.current = null;
    if (!start || isTransitioning) return;

    const horizontalDistance = event.clientX - start.x;
    const verticalDistance = event.clientY - start.y;
    if (Math.max(Math.abs(horizontalDistance), Math.abs(verticalDistance)) < 50) {
      setDrag({ x: 0, y: 0, isDragging: false });
      setIsRevealed((revealed) => !revealed);
      return;
    }

    const addToTest =
      verticalDistance > 0 && Math.abs(verticalDistance) > Math.abs(horizontalDistance);
    const isVertical = Math.abs(verticalDistance) > Math.abs(horizontalDistance);
    const distance = 620;

    setIsTransitioning(true);
    setDrag({
      x: isVertical ? 0 : horizontalDistance > 0 ? distance : -distance,
      y: isVertical ? (verticalDistance > 0 ? distance : -distance) : 0,
      isDragging: false,
    });
    window.setTimeout(() => {
      showNext(addToTest);
      setDrag({ x: 0, y: 0, isDragging: false });
      setIsTransitioning(false);
    }, 180);
  }

  return (
    <main className="study-screen">
      <header className="study-screen__header">
        <Link to="/" aria-label="홈으로 돌아가기">
          ‹
        </Link>
        <div>
          <span>오늘의 학습</span>
          <strong>{initialWords.length - studyWords.length + 1} / 20</strong>
        </div>
        <ThemeToggle />
      </header>

      {currentWord ? (
        <section className="word-deck" aria-label="단어 카드">
          {studyWords.slice(1, 3).map((word, index) => (
            <div className={`word-card word-card--stack-${index + 1}`} key={word.word} />
          ))}
          <article
            className={`word-card word-card--current${isRevealed ? " word-card--revealed" : ""}`}
            style={cardStyle}
            onPointerDown={handlePointerDown}
            onPointerMove={handlePointerMove}
            onPointerUp={handlePointerUp}
            onPointerCancel={() => setDrag({ x: 0, y: 0, isDragging: false })}
            role="button"
            tabIndex={0}
          >
            <h1>{currentWord.word}</h1>
            {isRevealed && (
              <div className="word-card__details">
                <p className="word-card__pronunciation">{currentWord.pronunciation}</p>
                <p className="word-card__meaning">{currentWord.meaning}</p>
                <hr />
                <p className="word-card__sentence">{currentWord.sentence}</p>
                <p className="word-card__translation">{currentWord.translation}</p>
              </div>
            )}
            {drag.y > 48 && <span className="word-card__test-hint">테스트 보기에 추가</span>}
          </article>
        </section>
      ) : (
        <section className="study-complete">
          <span>오늘의 학습 완료</span>
          <h1>수고했어요!</h1>
          <p>테스트 보기로 옮긴 단어는 아래 목록에서 확인할 수 있어요.</p>
        </section>
      )}

      <p className="swipe-guide">좌·우·위로 넘기기 · 아래로 내리면 테스트 보기에 추가</p>

      <section className="word-lists" aria-label="학습 목록">
        <div>
          <h2>학습 중</h2>
          <ul>
            {studyWords.slice(0, 3).map((word) => (
              <li key={word.word}>{word.word}</li>
            ))}
          </ul>
        </div>
        <div>
          <h2>테스트 보기</h2>
          <ul>
            {testWords.length ? (
              testWords.map((word) => <li key={word.word}>{word.word}</li>)
            ) : (
              <li className="word-lists__empty">아래로 내려보세요</li>
            )}
          </ul>
        </div>
      </section>
    </main>
  );
}
