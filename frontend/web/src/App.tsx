import { RecoilRoot } from 'recoil';
import '/font/font.css';
import Router from './router/Router.tsx';
import { RouterProvider } from 'react-router-dom';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
  return (
    <RecoilRoot>
      <RouterProvider router={Router} />
    </RecoilRoot>
  );
}

export default App;
