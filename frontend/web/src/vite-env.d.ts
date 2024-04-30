/// <reference types="vite/client" />
// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_CUSTOM_ENV_VARIABLE: string;
  readonly VITE_REACT_APP_API_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
