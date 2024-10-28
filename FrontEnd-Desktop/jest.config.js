module.exports = {
    setupFilesAfterEnv: ['<rootDir>/src/setupTests.js'],
    preset: 'react-native',
    transform: {
        '^.+\\.jsx?$': 'babel-jest',
    },
    moduleNameMapper: {
        '\\.(css|scss)$': 'identity-obj-proxy',
    },
    testEnvironment: 'jsdom',
};