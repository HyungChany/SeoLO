import L from 'leaflet';
import React, { useEffect, useState } from 'react';
import {
  ImageOverlay,
  Marker,
  Popup,
  useMap,
  useMapEvents,
} from 'react-leaflet';
import * as Typo from '@/components/typography/Typography.tsx';
import { useMutation, useQuery } from '@tanstack/react-query';
import {
  blueprintList,
  registrationMarker,
  simpleMachineCheck,
} from '@/apis/Main.ts';
import Dropdown from '../dropdown/DropDown.tsx';
import * as Color from '@/config/color/Color.ts';
import styled from 'styled-components';
// import { Button } from '@/components/button/Button.tsx';

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

interface MachineType {
  machine_id: number;
  machine_name: string;
}

interface DropDownType {
  value: number;
  label: string;
}

const Button = styled.div`
  width: 3.5rem;
  height: 1rem;
  border-radius: 0.3rem;
  border: 1px solid ${Color.GRAY100};
  padding: 0.7rem;
  align-items: center;
  justify-content: center;
  display: flex;
  cursor: pointer;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.2);
  background-color: ${Color.GRAY100};
  &:hover {
    background-color: ${Color.GREEN400};
    border-color: ${Color.GRAY300};
  }
`;
export const Leaflet = ({
  imageFile,
  modifyMode,
  selectedOption,
}: ImageMapProps): JSX.Element | null => {
  const [bounds, setBounds] = useState<L.LatLngBounds | null>(null);
  const [markers, setMarkers] = useState<L.LatLng[]>([]);
  const [machineSelected, setMachineSelected] = useState<DropDownType | null>(
    null,
  );
  const [popupPosition, setPopupPosition] = useState<L.LatLng | null>(null);

  const map = useMap();

  // 기존에 있는 마커 불러오기
  const { data: markerData } = useQuery({
    queryKey: ['markers', selectedOption],
    queryFn: () => blueprintList(selectedOption),
  });

  // 마커 등록
  const { mutate: markerMutate } = useMutation({
    mutationFn: registrationMarker,
  });

  // 마커 편집할 때 공장에 따른 기계 종류 불러오기
  const { data: machineData } = useQuery({
    queryKey: ['machine', selectedOption],
    queryFn: () => {
      if (selectedOption) {
        return simpleMachineCheck(selectedOption);
      }
      return null;
    },
  });

  // 드롭다운 옵션
  const machineDropdown = machineData?.map((machine: MachineType) => ({
    value: machine.machine_id,
    label: machine.machine_name,
  }));

  //드롭다운 선택
  const handleMachineChange = (option: DropDownType): void => {
    setMachineSelected(option);
  };
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
        setPopupPosition(e.latlng);
        // modifyMode가 true일 때만 마커 추가
      }
    },
  });
  useEffect(() => {
    if (markerData) {
      const propsMarkers = markerData.markers;
      propsMarkers.map((data: MarkerType) => {
        const newMarker = L.latLng(
          data.marker_locations.locationY,
          data.marker_locations.locationX,
        );
        console.log('뉴마커', newMarker);
        setMarkers((currentMarkers) => [...currentMarkers, newMarker]);
      });
    }
  }, [markerData]);
  const customIcon = L.icon({
    iconUrl: '@/../assets/images/Position.png',
    iconSize: [20, 25],
    iconAnchor: [10, 12.5],
  });

  if (!imageFile || !bounds) return null;
  const handleMarkerClick = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    e.stopPropagation();
    if (popupPosition) {
      setMarkers((currentMarkers) => [...currentMarkers, popupPosition]);
      if (machineSelected) {
        const formattedData = {
          machine_id: machineSelected.value,
          marker_x: popupPosition.lng,
          marker_y: popupPosition.lat,
        };
        markerMutate(formattedData);
      }
      setPopupPosition(null);
    }
  };
  return (
    <>
      {popupPosition && (
        <Popup position={popupPosition}>
          <div
            style={{
              display: 'flex',
              flexDirection: 'column',
              gap: '0.5rem',
              // justifyContent: 'flex-end',
              alignItems: 'flex-end',
              width: '100%',
              // alignItems: 'center',
              // textAlign: 'center',
            }}
          >
            <Dropdown
              options={machineDropdown}
              selectedOption={machineSelected}
              onOptionChange={handleMachineChange}
              placeholder="기계를 선택하세요"
            />
            <Button
              onClick={(e) => {
                handleMarkerClick(e);
              }}
            >
              <Typo.Detail0>완료</Typo.Detail0>
            </Button>
          </div>
        </Popup>
      )}

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
