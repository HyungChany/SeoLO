import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';

// 스타일 정의
const FooterContainer = styled.footer`
  position: relative;
  width: 100%;
  height: 5.625rem;
  background: #353935;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const LinksContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  gap: 5.625rem;
  position: absolute;
  width: 30.2rem;
  height: 1rem;
  left: 2.75rem;
`;

const Link = styled.a`
  font-family: 'NYJGothicM', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 1rem;
  color: ${Color.SNOW};
  cursor: pointer;
  text-decoration: none;
`;

const Copyright = styled.div`
  position: absolute;
  width: 12.25rem;
  height: 1rem;
  right: 2.75rem;
  font-family: 'NYJGothicM', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 1rem;
  line-height: 1rem;
  color: ${Color.SNOW};
`;

// 컴포넌트 정의
const Footer = () => (
  <FooterContainer>
    <LinksContainer>
      <Link href="#">이용약관</Link>
      <Link href="#">개인정보 처리 방침</Link>
      <Link href="#">기술 지원</Link>
    </LinksContainer>
    <Copyright>©SeoLo, All right reserved</Copyright>
  </FooterContainer>
);

export default Footer;
