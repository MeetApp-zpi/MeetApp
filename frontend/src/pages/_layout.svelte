<script lang="ts">
    import execute from '../lib/fetchWrapper';
    import { userDetails } from '../lib/stores';

    execute('users/details', 'GET')
        .then((r) => (r.status === 200 ? r.json() : null))
        .then((r) => {
            if (r !== null) {
                $userDetails = r;
            }
        });

    $: console.log($userDetails);
</script>

<slot />
