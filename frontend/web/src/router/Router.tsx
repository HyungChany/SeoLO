import CompanyInformation from '@/pages/CompanyInformation';
import { createBrowserRouter } from 'react-router-dom';

const checkRoutes = [
  { path: '/companycheck', element: <CompanyInformation /> },
];
const routes = [...checkRoutes];
const Router = createBrowserRouter(routes);
//
export default Router;
