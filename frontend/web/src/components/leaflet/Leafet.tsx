import L from 'leaflet';
import { useEffect, useState } from 'react';
import {
  ImageOverlay,
  Marker,
  Popup,
  useMap,
  useMapEvents,
} from 'react-leaflet';
import * as Typo from '@/components/typography/Typography.tsx';

interface ImageMapProps {
  imageFile: string | null;
}

export const Leaflet = ({ imageFile }: ImageMapProps): JSX.Element | null => {
  const [bounds, setBounds] = useState<L.LatLngBounds | null>(null);
  const [markers, setMarkers] = useState<L.LatLng[]>([]);
  const map = useMap();

  useEffect(() => {
    if (imageFile) {
      const img = new Image();
      img.onload = () => {
        const imgWidth = img.naturalWidth / 2; // 이미지 가로 크기의 반
        const imgHeight = img.naturalHeight / 2; // 이미지 세로 크기의 반
        const newBounds = L.latLngBounds(
          [-imgHeight, -imgWidth],
          [imgHeight, imgWidth],
        );
        setBounds(newBounds);
        map.fitBounds(newBounds); // 맵이 새 경계에 맞춰 조정
      };
      img.src = imageFile;
    }
  }, [imageFile, map]);

  useMapEvents({
    click: (e) => {
      const newMarker = e.latlng;
      setMarkers((currentMarkers) => [...currentMarkers, newMarker]);
    },
  });

  const customIcon = L.icon({
    iconUrl: '@/../assets/icons/Position.svg',
    iconSize: [30, 42],
    iconAnchor: [15, 42], // 아이콘의 앵커 포인트를 아이콘의 하단 중앙으로 설정
  });

  if (!imageFile || !bounds) return null;

  return (
    <>
      <ImageOverlay url={imageFile} bounds={bounds} />
      {markers.map((marker, idx) => (
        <Marker key={idx} position={marker} icon={customIcon}>
          <Popup>
            <Typo.Detail0>장비번호:123456</Typo.Detail0>
            <Typo.Detail0>작업자:김철수</Typo.Detail0>
            <Typo.Detail0>종료 예상 시간: mm.dd - hh.mm</Typo.Detail0>
          </Popup>
        </Marker>
      ))}
    </>
  );
};
