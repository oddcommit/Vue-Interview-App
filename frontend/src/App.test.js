import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const { findAllByText } = render(<App />);
  (async () => {
    const linkElement = await findAllByText('Login');
    expect(linkElement.length).toBe(2);
  })();
});
