import '../public/font/fonts.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Example } from '@/components/Example.tsx';

function App() {
  return (
    <Router>
      <Routes>
        <Route path={'/'} element={<Example />} />
      </Routes>
    </Router>
  );
}

export default App;
