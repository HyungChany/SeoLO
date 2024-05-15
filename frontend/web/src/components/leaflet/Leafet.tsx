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
import { useQuery } from '@tanstack/react-query';
import { blueprintList } from '@/apis/Main.ts';

interface ImageMapProps {
  imageFile: string | null;
  modifyMode: boolean;
  selectedOption: number;
}

interface MarkerLocationType {
  locationX: number;
  locationY: number;
}

interface MarkerType {
  marker_id: number;
  marker_locations: MarkerLocationType;
}
export const Leaflet = ({
  imageFile,
  modifyMode,
  selectedOption,
}: ImageMapProps): JSX.Element | null => {
  const [bounds, setBounds] = useState<L.LatLngBounds | null>(null);
  const [markers, setMarkers] = useState<L.LatLng[]>([]);
  const map = useMap();

  // 기존에 있는 마커 불러오기
  const { data: markerData } = useQuery({
    queryKey: ['markers', selectedOption],
    queryFn: () => blueprintList(selectedOption),
  });
  useEffect(() => {
    if (imageFile) {
      const img = new Image();
      img.onload = () => {
        const imgWidth = img.naturalWidth;
        const imgHeight = img.naturalHeight;
        const aspectRatio = imgWidth / imgHeight;

        // 이미지 비율 유지하면서 세로 기준 100%로 맞추기
        const mapHeight = map.getSize().y;
        const mapWidth = mapHeight * aspectRatio;
        const newBounds = L.latLngBounds(
          [-mapHeight / 2, -mapWidth / 2],
          [mapHeight / 2, mapWidth / 2],
        );

        setBounds(newBounds);
        map.fitBounds(newBounds);
      };
      img.src = imageFile;
    }
  }, [imageFile, map]);

  useMapEvents({
    click: (e) => {
      if (modifyMode) {
        // modifyMode가 true일 때만 마커 추가
        const newMarker = e.latlng;
        setMarkers((currentMarkers) => [...currentMarkers, newMarker]);
      }
    },
  });
  useEffect(() => {
    if (markerData) {
      const propsMarkers = markerData.markers;
      propsMarkers.map((data: MarkerType) => {
        const newMarker = L.latLng(
          data.marker_locations.locationX,
          data.marker_locations.locationY,
        );
        setMarkers((currentMarkers) => [...currentMarkers, newMarker]);
      });
    }
  }, [markerData]);
  const customIcon = L.icon({
    iconUrl: '@/../assets/images/Position.png',
    iconSize: [20, 25],
    iconAnchor: [15, 42],
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
