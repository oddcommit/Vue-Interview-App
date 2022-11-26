import { ChangeEvent, useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import axiosHttp from "../../utils/axios";
import "./register-view.css";

interface RegisterObject {
  [key: string]: string | number;
}

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
  const [registeredFormControls, setRegisteredFormControls] = useState<
    Array<RegisterFormControl>
  >([
    {
      key: "email",
      type: "email",
      placeholder: "Email",
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
        return;
      }

      if (
        registeredFormControl.key === "age" &&
        !Number(registeredFormControl.value)
      ) {
        registeredFormControl.error.isInvalid = true;
      }
    });

    if (
      registeredFormControlsCopy.filter((e) => e.error.isInvalid).length > 0
    ) {
      setRegisteredFormControls(registeredFormControlsCopy);
      return;
    }

    const axiosRegisterObject: RegisterObject = {};

    registeredFormControlsCopy.forEach(
      (registeredFormControl: RegisterFormControl) => {
        const { key, value } = registeredFormControl;

        axiosRegisterObject[key] = Number(value) ? Number(value) : value;
      }
    );

    axiosHttp
      .post("/register", axiosRegisterObject)
      .then((response) => {
        if (response.status == 201) {
          console.log("Successful");
        }
      })
      .catch(() => {
        console.error("Error registering");
      });
  };

  return (
    <>
      <div className="register-container">
        {registeredFormControls.map(
          (registerFormControl: RegisterFormControl, index: number) => {
            if (registerFormControl.type !== "select") {
              return (
                <FloatingLabel
                  key={registerFormControl.key}
                  controlId={`formBasic${registerFormControl.key}`}
                  label={
                    registerFormControl.error.isInvalid
                      ? registerFormControl.error.errorMessage
                      : registerFormControl.placeholder
                  }
                  className={`mb-3 ${
                    registerFormControl.error.isInvalid ? "register-error" : ""
                  } `}
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
                className={`mb-3 ${
                  registerFormControl.error.isInvalid ? "register-error" : ""
                }`}
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
                  <option className="register-gender-select" value="MALE">
                    Male
                  </option>
                  <option className="register-gender-select" value="FEMALE">
                    Female
                  </option>
                </Form.Select>
              </FloatingLabel>
            );
          }
        )}

        <Button onClick={register}>Register</Button>
      </div>
    </>
  );
};

export default RegisterView;
