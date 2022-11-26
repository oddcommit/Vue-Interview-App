import { ChangeEvent, useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import { useDispatch } from "react-redux";
import "./register-view.css";

interface RegisterFormControl {
  key: string;
  type: string;
  placeholder: string;
  value: string;
  error: {
    isInvalid: boolean;
    errorMessage: string;
  };
}

const RegisterView = () => {
  const [registeredFormControls, setRegisteredFormControls] = useState([
    {
      key: "email",
      type: "email",
      placeholder: "Email",
      isInvalid: false,
      value: "",
      error: {
        isInvalid: false,
        errorMessage: "Email can not be empty",
      },
    },
    {
      key: "password",
      type: "password",
      placeholder: "Password",
      value: "",
      error: {
        isInvalid: false,
        errorMessage: "Password can not be empty",
      },
    },
    {
      key: "name",
      type: "email",
      placeholder: "Name",
      value: "",
      error: {
        isInvalid: false,
        errorMessage: "Name can not be empty",
      },
    },
    {
      key: "age",
      type: "email",
      placeholder: "Age",
      value: "",
      error: {
        isInvalid: false,
        errorMessage: "Age must be a number and can not be empty",
      },
    },
    {
      key: "gender",
      type: "select",
      placeholder: "Gender",
      isInvalid: false,
      value: "",
      error: {
        isInvalid: false,
        errorMessage: "Please select a gender",
      },
    },
  ]);

  const onChangeRegisterData = (
    event: ChangeEvent<HTMLInputElement>,
    index: number
  ): void => {
    changeRegisterData(index, event.target.value);
  };

  const onChangeRegisterSelectData = (
    event: ChangeEvent<HTMLSelectElement>,
    index: number
  ): void => {
    changeRegisterData(index, event.target.value);
  };

  const changeRegisterData = (index: number, value: string): void => {
    const registeredFormControlsCopy: Array<RegisterFormControl> = [
      ...registeredFormControls,
    ];

    let registeredFormControlCopy = registeredFormControlsCopy[index];

    registeredFormControlCopy.value = value;
    if (registeredFormControlCopy.value) {
      registeredFormControlCopy.error.isInvalid = false;
    }

    setRegisteredFormControls(registeredFormControlsCopy);
  };

  const register = () => {
    const registeredFormControlsCopy: Array<RegisterFormControl> = [
      ...registeredFormControls,
    ];

    registeredFormControlsCopy.forEach((registeredFormControl) => {
      if (!registeredFormControl.value) {
        registeredFormControl.error.isInvalid = true;
      }
    });

    if (registeredFormControlsCopy.filter((e) => e.error.isInvalid)) {
      setRegisteredFormControls(registeredFormControlsCopy);
      return;
    }
  };

  return (
    <>
      <div className="center">
        <div className="login-container">
          {registeredFormControls.map(
            (registerFormControl: RegisterFormControl, index: number) => {
              if (registerFormControl.type !== "select") {
                return (
                  <FloatingLabel
                    key={registerFormControl.key}
                    controlId={`formBasic${registerFormControl.key}`}
                    label={registerFormControl.placeholder}
                    className="mb-3"
                  >
                    <Form.Control
                      name={registerFormControl.key}
                      type={registerFormControl.type}
                      placeholder={registerFormControl.placeholder}
                      onChange={(event: ChangeEvent<HTMLInputElement>) =>
                        onChangeRegisterData(event, index)
                      }
                      className={
                        registerFormControl.error.isInvalid ? "is-invalid" : ""
                      }
                    />
                  </FloatingLabel>
                );
              }

              return (
                <FloatingLabel
                  controlId={`formBasic${registerFormControl.key}`}
                  label="Gender"
                  key={registerFormControl.key}
                >
                  <Form.Select
                    aria-label="Floating label select example"
                    onChange={(event: ChangeEvent<HTMLSelectElement>) =>
                      onChangeRegisterSelectData(event, index)
                    }
                    className={
                      registerFormControl.error.isInvalid ? "is-invalid" : ""
                    }
                  >
                    <option value="">Choose an option</option>
                    <option value="FEMALE">Female</option>
                    <option value="MALE">Male</option>
                  </Form.Select>
                </FloatingLabel>
              );
            }
          )}

          <Button onClick={register}>Register</Button>
        </div>
      </div>
    </>
  );
};

export default RegisterView;
