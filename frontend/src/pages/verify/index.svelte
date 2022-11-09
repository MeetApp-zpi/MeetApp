<script lang="ts">
    let data = '';

    $: {
        fetch(`http://localhost:5173/api/verifyTokenBearer`, {
            headers: {
                Authorization:
                    'Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjcxM2ZkNjhjOTY2ZTI5MzgwOTgxZWRjMDE2NGEyZjZjMDZjNTcwMmEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNTUwNjk0MzY4MTczLW81NGJvOWo2MG10ZmVrcnBxcmticjFzYWFtMXBnNDIxLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNTUwNjk0MzY4MTczLW81NGJvOWo2MG10ZmVrcnBxcmticjFzYWFtMXBnNDIxLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTE0OTAzNDI5ODk4MjQwODEyMzIwIiwiZW1haWwiOiJtZWV0YXBwLnpwaUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6InY1STBjelBQNEhNX045Y2xIbEszZmciLCJpYXQiOjE2NjgwMTYxNTgsImV4cCI6MTY2ODAxOTc1OH0.Qyg_K8ublAGFGkBGVYGpO3Xqi6KGo7FQNbSCtJXw170yLRef-GnGa872YTAkyfnSUQOaQliIDH3__saYunmCk9T-alzsZ1xc3kcgxTUvStZhw_MXr18a-3yR8CZX2abLJ5Gwv1ksaEn5CmwfAf58JhbgyOZ3Na5l5FUiLTbwRdGmM6eifRRPWbD2OE0o4vyraRoHJqRW5ANOu0woMW4vXu7P25byh7LuGq2xL8anduloo6OU1PYKhVehIu9NHN1ItNe_C30rg7b9fpfou31xn5fdm-VXDVvd5Y4kGQJlYX3AQpWyHb6Mu6N954KuxN-WWrO46uihixvZlbgR_JwXMQ'
            }
        })
            .then((res) => res.text())
            .then((res) => (data = res))
            .catch((err) => console.error(err));
    }
</script>

<h1>
    Welcome {data}
</h1>
