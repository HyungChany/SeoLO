import { RecoilRoot } from 'recoil';
import '@/font/fonts.css';
import Router from '@/router/Router';
import { RouterProvider } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import useSSE from './hook/useSSE';
import NotificationModal from './components/modal/NotificationModal';

function App() {
  const queryClient = new QueryClient();

  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <SSEHandler />
        <RouterProvider router={Router} />
        <NotificationModal />
      </RecoilRoot>
    </QueryClientProvider>
  );
}

const SSEHandler = () => {
  useSSE();
  return null;
};

export default App;
