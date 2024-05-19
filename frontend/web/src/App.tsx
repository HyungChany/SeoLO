import { RecoilRoot } from 'recoil';
import '@/font/fonts.css';
import Router from '@/router/Router.tsx';
import { RouterProvider } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import useSSE from './hook/useSSE.tsx';
import NotificationModal from './components/modal/NotificationModal.tsx';

const SSEHandler = () => {
  useSSE();
  return null;
};
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

export default App;
