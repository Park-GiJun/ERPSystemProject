// src/utils/dateUtils.js

/**
 * 날짜를 'YYYY-MM-DD' 형식으로 포맷팅
 * @param {string | Date} date - 날짜 문자열 또는 Date 객체
 * @returns {string} 포맷팅된 날짜 문자열
 */
export const formatDate = (date) => {
  if (!date) return '';

  const d = new Date(date);
  if (isNaN(d.getTime())) return '';

  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
};

/**
 * 날짜를 'YYYY-MM-DD HH:mm' 형식으로 포맷팅
 * @param {string | Date} date - 날짜 문자열 또는 Date 객체
 * @returns {string} 포맷팅된 날짜와 시간 문자열
 */
export const formatDateTime = (date) => {
  if (!date) return '';

  const d = new Date(date);
  if (isNaN(d.getTime())) return '';

  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

/**
 * 날짜를 'YYYY년 MM월 DD일' 형식으로 포맷팅
 * @param {string | Date} date - 날짜 문자열 또는 Date 객체
 * @returns {string} 포맷팅된 한글 날짜 문자열
 */
export const formatDateKorean = (date) => {
  if (!date) return '';

  const d = new Date(date);
  if (isNaN(d.getTime())) return '';

  const year = d.getFullYear();
  const month = d.getMonth() + 1;
  const day = d.getDate();

  return `${year}년 ${month}월 ${day}일`;
};

/**
 * 두 날짜 사이의 일수 계산
 * @param {string | Date} startDate - 시작 날짜
 * @param {string | Date} endDate - 종료 날짜
 * @returns {number} 두 날짜 사이의 일수
 */
export const getDaysBetween = (startDate, endDate) => {
  const start = new Date(startDate);
  const end = new Date(endDate);

  if (isNaN(start.getTime()) || isNaN(end.getTime())) return 0;

  const diffTime = Math.abs(end - start);
  return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
};

/**
 * 날짜가 지났는지 확인
 * @param {string | Date} date - 확인할 날짜
 * @returns {boolean} 지난 날짜인지 여부
 */
export const isDatePassed = (date) => {
  const d = new Date(date);
  const today = new Date();

  if (isNaN(d.getTime())) return false;

  return d < today;
};

/**
 * 날짜가 유효한지 확인
 * @param {string | Date} date - 확인할 날짜
 * @returns {boolean} 유효한 날짜인지 여부
 */
export const isValidDate = (date) => {
  const d = new Date(date);
  return !isNaN(d.getTime());
};

/**
 * 상대적인 날짜 표시 (예: '3일 전', '방금 전')
 * @param {string | Date} date - 날짜
 * @returns {string} 상대적 시간 문자열
 */
export const getRelativeTimeString = (date) => {
  const d = new Date(date);
  if (isNaN(d.getTime())) return '';

  const now = new Date();
  const diffTime = now - d;
  const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));
  const diffHours = Math.floor(diffTime / (1000 * 60 * 60));
  const diffMinutes = Math.floor(diffTime / (1000 * 60));

  if (diffDays > 30) {
    return formatDate(date);
  } else if (diffDays > 0) {
    return `${diffDays}일 전`;
  } else if (diffHours > 0) {
    return `${diffHours}시간 전`;
  } else if (diffMinutes > 0) {
    return `${diffMinutes}분 전`;
  } else {
    return '방금 전';
  }
};
