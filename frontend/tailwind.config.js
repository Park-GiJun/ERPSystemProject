/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'primary': '#2563eb',
        'secondary': '#475569',
        'accent': '#0EA5E9',
      },
    },
  },
  plugins: [],
}
