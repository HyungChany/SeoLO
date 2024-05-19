import styled from 'styled-components';
import * as Color from '@/config/color/Color.ts';

const FooterContainer = styled.footer`
  height: 4rem;
  width: 100%;
  background: #353935;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 500px;
  overflow-x: hidden;
`;

const LinksContainer = styled.div`
  width: 400px;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  padding-left: 3rem;
  gap: 5rem;
`;

const Link = styled.div`
  font-family: 'NYJGothicR', sans-serif;
  font-style: normal;
  font-weight: 300;
  font-size: 1rem;
  color: ${Color.SNOW};
  cursor: pointer;
  text-decoration: none;
  white-space: nowrap;
`;

const Copyright = styled.div`
  /* flex-grow: 1; */
  display: flex;
  padding-right: 3rem;
  font-family: 'NYJGothicR', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 1rem;
  color: ${Color.SNOW};
  white-space: nowrap;
  overflow-x: hidden;
`;

const Footer = () => (
  <FooterContainer>
    <LinksContainer>
      <Link>이용약관</Link>
      <Link>개인정보 처리 방침</Link>
      <Link>기술 지원</Link>
    </LinksContainer>
    <Copyright>©SeoLo, All right reserved</Copyright>
  </FooterContainer>
);

export default Footer;
