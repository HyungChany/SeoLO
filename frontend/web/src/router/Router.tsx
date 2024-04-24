import { Example } from '@/components/Example.tsx';
import CompanyInformation from '@/pages/CompanyInformation.tsx';
import { createBrowserRouter } from 'react-router-dom';
const mainRoutes = [{ path: '/', element: <Example /> }];
const checkRoutes = [{ path: '/information', element: <CompanyInformation /> }];
const routes = [...checkRoutes, ...mainRoutes];
const Router = createBrowserRouter(routes);

export default Router;
