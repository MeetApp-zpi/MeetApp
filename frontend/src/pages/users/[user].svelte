<script lang="ts">
    let data = '';

    $: {
        fetch(`http://localhost:5173/api/users/categories`)
            .then((res) => res.text())
            .then((res) => (data = res))
            .catch((err) => console.error(err));
    }
</script>

<h1>
    Welcome {data}
</h1>
