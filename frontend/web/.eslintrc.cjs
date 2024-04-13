module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:react-hooks/recommended',
    'airbnb-typescript', // 추가
    'plugin:prettier/recommended', // 추가
  ],
  ignorePatterns: ['dist', '.eslintrc.cjs', 'vite.config.ts'],
  parser: '@typescript-eslint/parser',
  parserOptions: {
    project: './tsconfig.json', // TypeScript 설정 파일 경로 지정
  },
  plugins: ['react-refresh', 'import', 'react'],
  rules: {
    'react-refresh/only-export-components': [
      'warn',
      { allowConstantExport: true },
    ],
    'import/extensions': ['error', 'always', { ignorePackages: true }],
    'import/no-extraneous-dependencies': ['error', { devDependencies: true }],
    'react/jsx-filename-extension': [
      1,
      { extensions: ['.jsx', '.js', 'ts', 'tsx'] },
    ],
  },
};
