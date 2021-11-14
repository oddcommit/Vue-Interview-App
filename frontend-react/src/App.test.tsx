import { render, screen } from "@testing-library/react";
import App from "./App";
import * as redux from "react-redux";

jest.mock("react-bootstrap/esm/Spinner", () => ({ Spinner }: any) => (
  <>{Spinner}</>
));

jest.mock("react-redux", () => ({
  useSelector: () => jest.fn(),
  useDispatch: () => jest.fn(),
}));

const spy = jest.spyOn(redux, "useSelector");
spy.mockReturnValue({ isApplicationLoading: true });

test("is loading showing", () => {
  render(<App />);
  const linkElement = screen.getByText(/Loading.../i);
  expect(linkElement).toBeInTheDocument();
});
