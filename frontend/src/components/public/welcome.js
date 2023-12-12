import axios from "axios";
import React, { useEffect, useState } from "react";
import { getHostNameServer } from "../../services/config";

const Welcome = () => {

    const [users, setUsers] = useState([]);
    const [isValid, setIsValid] = useState(false);
    const [user, setUser] = useState({ userName: '', fullName: '', urlImageProfile: '' });

    useEffect(() => {
        getUsers();
    }, []);


    const getUsers = async () => {
        await axios.get(`${getHostNameServer()}/users`).then((response) => {
            setUsers(response.data);
            console.log(response.data);
        }).catch((error) => {
            console.log(error);
        });
    }

    const saveUsers = async (e) => {
        e.preventDefault();
        await axios.post(`${getHostNameServer()}/users`, user).then((response) => {
            if (response.status === 404) {
                setIsValid(false);
            } else {
                setIsValid(true);
                getUsers();
            }
        }).catch((error) => {
            console.log(error);
        });
    }

    const handleChangeUser = (event) => {
        const fields = { userName: user.userName, fullName: user.fullName, urlImageProfile: user.urlImageProfile };
        fields[event.target.name] = event.target.value;
        setUser(fields);
    }

    return (
        <div>
            <div className="mb-3">
                {isValid ? (
                    <div className="alert alert-success" role="alert">
                        Saved user
                    </div>
                ) : <div className="alert alert-danger" role="alert">
                    User is not valid
                </div>
                }
                <form onSubmit={saveUsers}>
                    <label htmlFor="exampleFormControlInput1" className="form-label">Username</label>
                    <input type="text" className="form-control" name="userName" onChange={handleChangeUser} id="exampleFormControlInput1" placeholder="username" required />
                    <button className="btn btn-primary mt-3 btn-sm" type="submit">Save</button>
                </form>
            </div>
            <h1>List Users</h1>
            <table className="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Username</th>
                        <th scope="col">Full Name</th>
                        <th scope="col">Url Avatar</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map((user, index) => {
                        return (
                            <tr key={user.id}>
                                <th scope="row">{index}</th>
                                <td>{user.userName}</td>
                                <td>{user.fullName}</td>
                                <td>{user.urlImageProfile}</td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
        </div>
    );
}
export default Welcome;