/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}", // Quét tất cả các file code của React
  ],
  theme: {
    extend: {
      // Sau này chúng ta sẽ cấu hình màu chủ đạo (Xanh dương/Xanh ngọc) ở đây
      colors: {
        primary: '#0ea5e9', // Ví dụ màu Sky Blue của Tailwind
      }
    },
  },
  plugins: [],
}