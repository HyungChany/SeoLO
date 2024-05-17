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
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import {
  blueprintList,
  deleteMarker,
  detailMarker,
  registrationMarker,
  simpleMachineCheck,
} from '@/apis/Main.ts';
import Dropdown from '../dropdown/DropDown.tsx';
import * as Color from '@/config/color/Color.ts';
import styled from 'styled-components';
import { useRecoilValue } from 'recoil';
// import { Button } from '@/components/button/Button.tsx';
import { notificationEventsState } from '@/recoil/sseState.tsx';
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
  now_task_status: string | null;
}

interface MachineType {
  machine_id: number;
  machine_name: string;
}

interface DropDownType {
  value: number;
  label: string;
}

interface ButtonProps {
  width: string;
  height: string;
  $hoverBackgroundColor: string;
}

interface MarkerStateType {
  id: number | null;
  position: L.LatLng;
  now: string | null;
}

interface ButtonBoxProps {
  justifyContent: string;
}

interface MarkerDetailType {
  machine_num: string;
  machine_name: string;
  worker_name: string;
  estimated_end_time: string;
  content: string;
}
const Button = styled.div<ButtonProps>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  border-radius: 0.3rem;
  border: 1px solid ${Color.GRAY100};
  /* padding: 0.7rem; */
  align-items: center;
  justify-content: center;
  display: flex;
  cursor: pointer;
  box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.2);
  background-color: ${Color.GRAY100};
  &:hover {
    background-color: ${(props) => props.$hoverBackgroundColor};
    border-color: ${Color.GRAY300};
  }
`;
const MarkerBox = styled.div`
  min-width: 200px;
  display: flex;
  flex-direction: column;
`;
const ButtonBox = styled.div<ButtonBoxProps>`
  width: 100%;
  display: flex;
  justify-content: ${(props) => props.justifyContent};
  gap: 1rem;
  margin-top: 0.5rem;
`;
export const Leaflet = ({
  imageFile,
  modifyMode,
  selectedOption,
}: ImageMapProps): JSX.Element | null => {
  const [bounds, setBounds] = useState<L.LatLngBounds | null>(null);
  const [markers, setMarkers] = useState<MarkerStateType[]>([]);
  const [machineSelected, setMachineSelected] = useState<DropDownType | null>(
    null,
  );
  const [popupPosition, setPopupPosition] = useState<L.LatLng | null>(null);
  const events = useRecoilValue(notificationEventsState);
  const map = useMap();
  const queryClient = useQueryClient();
  const [id, setId] = useState<number | null>(null);
  // 기존에 있는 마커 불러오기
  const { data: markerData } = useQuery({
    queryKey: ['markers', selectedOption, events],
    queryFn: () => blueprintList(selectedOption),
  });

  // 마커 등록
  const { mutate: markerMutate } = useMutation({
    mutationFn: registrationMarker,
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['markers'] }),
  });
  // 마커 삭제
  const { mutate: deleteMutate } = useMutation({
    mutationFn: deleteMarker,
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['markers'] }),
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
  // 마커 정보 불러오기
  const { data: markerDetail } = useQuery<MarkerDetailType>({
    queryKey: ['markerDetail', id],
    queryFn: () => detailMarker(id!),
    enabled: id !== null,
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
      setMarkers([]);
      propsMarkers.map((data: MarkerType) => {
        const newMarker = {
          id: data.marker_id,
          position: L.latLng(
            data.marker_locations.locationY,
            data.marker_locations.locationX,
          ),
          now: data.now_task_status,
        };
        setMarkers((currentMarkers) => [...currentMarkers, newMarker]);
      });
    }
  }, [markerData]);
  // 알맞은 icon가져오기
  const lockedIcon = L.icon({
    iconUrl: '/Locked.png',
    iconSize: [50, 50],
    iconAnchor: [25, 25],
  });
  const openedIcon = L.icon({
    iconUrl: '/OpenLocker.png',
    iconSize: [50, 40],
    iconAnchor: [25, 20],
  });

  if (!imageFile || !bounds) return null;
  const handleMarkerClick = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
  ) => {
    e.stopPropagation();
    if (popupPosition) {
      // setMarkers((currentMarkers) => [...currentMarkers, popupPosition]);
      if (machineSelected) {
        const formattedData = {
          machine_id: machineSelected.value,
          marker_x: popupPosition.lng,
          marker_y: popupPosition.lat,
        };
        markerMutate(formattedData);
      } else alert('기계를 선택해주세요');

      setPopupPosition(null);
    }
  };
  const handleDeleteClick = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>,
    index: number,
  ) => {
    e.stopPropagation();
    if (window.confirm('삭제하시겠습니까?')) {
      deleteMutate(index);
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
            <ButtonBox justifyContent="flex-end">
              <Button
                width="3.5rem"
                height="1.7rem"
                $hoverBackgroundColor={Color.GREEN400}
                onClick={(e) => {
                  handleMarkerClick(e);
                }}
              >
                <Typo.Detail0>완료</Typo.Detail0>
              </Button>
            </ButtonBox>
          </div>
        </Popup>
      )}

      <ImageOverlay url={imageFile} bounds={bounds} />
      {markers.map((marker) => (
        <Marker
          key={marker.id}
          position={marker.position}
          icon={
            marker.now === null || marker.now === 'ISSUED'
              ? openedIcon
              : lockedIcon
          }
          eventHandlers={{
            click: () => {
              if (marker.id) {
                setId(marker.id);
              }
            },
          }}
        >
          <Popup>
            {markerDetail ? (
              <MarkerBox>
                {markerDetail.estimated_end_time != null ? (
                  <>
                    <div
                      style={{
                        justifyContent: 'center',
                        alignItems: 'center',
                        textAlign: 'center',
                      }}
                    >
                      <div
                        style={{
                          fontSize: '1.25rem',
                          color: `${Color.SAMSUNG_BLUE}`,
                          fontWeight: 'bold',
                        }}
                      >
                        장비 정보
                      </div>
                    </div>
                    <Typo.Detail0>
                      장비명: {markerDetail.machine_name}
                    </Typo.Detail0>
                    <Typo.Detail0>
                      작업자: {markerDetail.worker_name}
                    </Typo.Detail0>
                    <Typo.Detail0>
                      종료 예상 시간: {markerDetail.estimated_end_time}
                    </Typo.Detail0>
                  </>
                ) : (
                  <>
                    <div
                      style={{
                        justifyContent: 'center',
                        alignItems: 'center',
                        textAlign: 'center',
                        marginBottom: '1rem',
                      }}
                    >
                      <div
                        style={{
                          fontSize: '1.25rem',
                          color: `${Color.SAMSUNG_BLUE}`,
                          fontWeight: 'bold',
                        }}
                      >
                        현재 작업중이 아닙니다
                      </div>
                    </div>
                    <Typo.Detail0>
                      장비명: {markerDetail.machine_name}
                    </Typo.Detail0>
                    <Typo.Detail0>
                      장비번호: {markerDetail.machine_num}
                    </Typo.Detail0>
                  </>
                )}

                <ButtonBox justifyContent="flex-end">
                  <Button
                    width="3rem"
                    height="1.2rem"
                    onClick={(e) => {
                      if (marker.id) {
                        handleDeleteClick(e, marker.id);
                      }
                    }}
                    $hoverBackgroundColor={Color.RED1}
                  >
                    <Typo.Detail0>삭제</Typo.Detail0>
                  </Button>
                </ButtonBox>
              </MarkerBox>
            ) : (
              <Typo.Detail0>로딩 중 입니다</Typo.Detail0>
            )}
          </Popup>
        </Marker>
      ))}
    </>
  );
};
