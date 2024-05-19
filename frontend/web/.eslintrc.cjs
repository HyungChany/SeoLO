module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:react-hooks/recommended',
    'airbnb-typescript',
    'plugin:prettier/recommended',
  ],
  ignorePatterns: ['dist', '.eslintrc.cjs', 'vite.config.ts'],
  parser: '@typescript-eslint/parser',
  parserOptions: {
    project: './tsconfig.json',
    tsconfigRootDir: __dirname,
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
      { extensions: ['.jsx', '.js', '.ts', '.tsx'] },
    ],
    'prettier/prettier': [
      'error',
      {
        endOfLine: 'auto',
        semi: true,
        singleQuote: true,
        printWidth: 80,
        tabWidth: 2,
        trailingComma: 'all',
        bracketSpacing: true,
      },
    ],
  },
};
