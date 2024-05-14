declare module 'event-source-polyfill' {
    export class EventSourcePolyfill {
      constructor(url: string, eventSourceInitDict?: EventSourceInit);
  
      onerror: (this: EventSource, ev: MessageEvent) => any;
      onmessage: (this: EventSource, ev: MessageEvent) => any;
      onopen: (this: EventSource, ev: MessageEvent) => any;
      close: () => void;
    }
  
    export interface EventSourceInit {
      withCredentials?: boolean;
      headers?: Record<string, string>;
      heartbeatTimeout?: number;
    }
  }
  