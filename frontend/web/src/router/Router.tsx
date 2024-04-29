import { Example } from '@/components/Example.tsx';
import styled from 'styled-components';
import CompanyInformation from '@/pages/CompanyInformation.tsx';
import { createBrowserRouter } from 'react-router-dom';
import Footer from '@/components/footer/Footer.tsx';
import Navigation from '@/components/navigation/Navigation.tsx';
import LoginPage from '@/pages/LoginPage.tsx';
import MainPage from '@/pages/MainPage.tsx';
import React from 'react';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100dvh;
`;

const Content = styled.div`
  flex: 1;
  overflow-y: auto;
`;

const With = (element: JSX.Element) => (
  <Container>
    <Navigation />
    <Content>{element}</Content>
    <Footer />
  </Container>
);

const mainRoutes = [{ path: '/', element: With(<MainPage />) }];

const loginRoute = { path: '/login', element: <LoginPage /> };
const informationRoute = {
  path: '/information',
  element: <CompanyInformation />,
};
const exampleRoute = { path: '/example', element: <Example /> };

const routes = [informationRoute, ...mainRoutes, loginRoute, exampleRoute];

const Router = createBrowserRouter(routes);

export default Router;
