import styled from 'styled-components';

// 스타일 정의
const FooterContainer = styled.footer`
  position: absolute;
  width: 100%;
  height: 90px;
  left: 0;
  bottom: 0;
  background: #353935;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const LinksContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  padding: 0;
  gap: 90px;
  position: absolute;
  width: 483px;
  height: 16px;
  left: 44px;
  top: 37px;
`;

const Link = styled.a`
  font-family: 'NYJGothicM', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  color: #fffafa;
  cursor: pointer;
`;

const Copyright = styled.div`
  position: absolute;
  width: 198px;
  height: 16px;
  right: 42px;
  top: 37px;
  font-family: 'NYJGothicM', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  color: #fffafa;
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
