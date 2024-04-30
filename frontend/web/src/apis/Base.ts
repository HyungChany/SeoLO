import axios from 'axios';
const baseURL = import.meta.env.VITE_REACT_APP_API_URL;

export const api = axios.create({
  baseURL,
});
