<script lang="ts">
    export let user: String;
    let data = '';

    $: {
        fetch(`http://localhost:8080/api/users/${user}`)
            .then((res) => res.text())
            .then((res) => (data = res))
            .catch((err) => console.error(err));
    }
</script>

<h1>
    Welcome {data}
</h1>
