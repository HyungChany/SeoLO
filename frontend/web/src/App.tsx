import { RecoilRoot } from 'recoil';
import '@/font/fonts.css';
import Router from '@/router/Router.tsx';
import { RouterProvider } from 'react-router-dom';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
function App() {
  const queryClient = new QueryClient();
  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <RouterProvider router={Router} />
      </RecoilRoot>
    </QueryClientProvider>
  );
}

export default App;
