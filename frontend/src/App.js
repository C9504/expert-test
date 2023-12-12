import { Routes, Route } from 'react-router-dom';
import Layout from './components/public/layout';
import Welcome from './components/public/welcome';

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Layout />} >
          <Route index element={<Welcome />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
