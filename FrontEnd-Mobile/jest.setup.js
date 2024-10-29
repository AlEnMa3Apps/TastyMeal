// Extiende las expectativas de Jest con las de Testing Library
import '@testing-library/jest-native/extend-expect'

// Mock de AsyncStorage
jest.mock('@react-native-async-storage/async-storage', () => require('@react-native-async-storage/async-storage/jest/async-storage-mock'))
