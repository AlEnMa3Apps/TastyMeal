const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

module.exports = {
  entry: './src/index.js', // Punto de entrada de tu aplicación
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'bundle.js', // Nombre del bundle de salida
  },
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,  // Procesar tanto archivos .js como .jsx
        exclude: /node_modules/,  // Excluir node_modules
        use: {
          loader: 'babel-loader',  // Usa babel-loader para procesar los archivos
          options: {
            presets: ['@babel/preset-env', '@babel/preset-react'],  // Configuración de presets
          },
        },
      },
      {
        test: /\.css$/,  // Para archivos CSS (si los tienes)
        use: ['style-loader', 'css-loader'],
      },
    ],
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),  // Aquí alias @ apunta a la carpeta src
    },
    extensions: ['.js', '.jsx', '.ts', '.tsx'],  // Asegúrate de tener las extensiones necesarias
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html', // Template HTML
    }),
  ],
  devServer: {
    static: './dist',
    port: 3000,
  },
};
