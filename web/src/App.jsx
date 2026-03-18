import React from 'react';

function App() {
  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-50">
      <div className="p-8 max-w-sm w-full bg-white shadow-xl rounded-2xl border border-slate-100 text-center">
        <h1 className="text-3xl font-bold text-primary mb-4">
          Hello Tailwind! 🚀
        </h1>
        <p className="text-slate-600 mb-6">
          Hệ thống Quản lý Trung tâm Tiếng Anh đã sẵn sàng lên giao diện.
        </p>
        <button className="w-full py-2 px-4 bg-primary text-white font-semibold rounded-lg shadow-md hover:bg-blue-600 transition duration-300">
          Bắt đầu ngay
        </button>
      </div>
    </div>
  );
}

export default App;