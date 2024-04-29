import L from 'leaflet';
import { useEffect, useState } from 'react';
import { ImageOverlay, Marker, useMap, useMapEvents } from 'react-leaflet';

interface ImageMapProps {
  imageFile: string | null;
}

export const Leaflet = ({ imageFile }: ImageMapProps): JSX.Element | null => {
  const [bounds, setBounds] = useState<L.LatLngBounds | null>(null);
  const [markers, setMarkers] = useState<L.LatLng[]>([]);
  const map = useMap();

  // 이미지 파일이 변경되면 bounds를 설정. 이때 bounds는 이미지의 크기에 맞게 설정
  useEffect(() => {
    if (imageFile) {
      const img = new Image();
      img.onload = () => {
        const imgWidth = img.naturalWidth / 2;
        const imgHeight = img.naturalHeight / 2;
        const newBounds = L.latLngBounds(
          [-imgHeight, -imgWidth],
          [imgHeight, imgWidth],
        );
        setBounds(newBounds);
        map.fitBounds(newBounds);
      };
      img.src = imageFile;
    }
  }, [imageFile, map]);

  // 클릭 이벤트를 통해 마커 추가
  useMapEvents({
    click: (e) => {
      // 클릭된 위치의 위경도값 할당.
      const newMarker = e.latlng;
      // 기존 배열에 마커 위경도값 추가
      setMarkers((currentMarkers) => [...currentMarkers, newMarker]);
    },
  });

  if (!imageFile || !bounds) return null;

  return (
    <>
      <ImageOverlay url={imageFile} bounds={bounds} />
      {markers.map((marker, idx) => (
        <Marker key={idx} position={marker} />
      ))}
    </>
  );
};
