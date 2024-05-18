// PrivateRoute.tsx
import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }: { children: React.ReactNode }) => {
  const accessToken = sessionStorage.getItem('accessToken');

  const [firstRender, setFirstRender] = useState<boolean>(true);
  useEffect(() => {
    if (!accessToken && !firstRender) {
      alert('로그인이 만료되었습니다');
    }
    setFirstRender(false);
  }, [accessToken]);

  if (!accessToken) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>; // 자식 컴포넌트를 Fragment로 감싸서 반환
};

export default PrivateRoute;
