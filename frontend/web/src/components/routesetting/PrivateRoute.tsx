// PrivateRoute.tsx
import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }: { children: React.ReactNode }) => {
  const accessToken = sessionStorage.getItem('accessToken');

  if (!accessToken) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>; // 자식 컴포넌트를 Fragment로 감싸서 반환
};

export default PrivateRoute;
