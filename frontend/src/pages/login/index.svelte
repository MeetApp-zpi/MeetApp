<script lang="ts">
    import { url } from '@roxi/routify';

    const gotoToGoogleLogin = () => {
        window.location.href = 'http://localhost:8080/oauth2/authorization/google';
    };
</script>

<div class="h-screen bg-grass relative">
    <div
        class="bg-ivory rounded-xl px-10 py-2 flex flex-col mx-4 h-48 my-auto right-0 left-0 top-0 bottom-0 absolute
        justify-between lg:w-1/3 lg:mx-auto"
    >
        <div class="w-24 self-center">
            <img src="logo.png" alt="MeetApp" />
        </div>
        <button class="flex flex-row self-center shadow-md border-snow-drift border-2 py-2 px-2 rounded-md" on:click={gotoToGoogleLogin}>
            <div class="w-5 mr-4 align-middle"><img src="google.png" alt="Google" /></div>
            <div class="align-middle text-cocoa text-sm">Sign in with Google</div>
        </button>
        <div class="self-center text-sage">
            <a href={$url('/termsOfUse')}>Regulamin</a>
        </div>
    </div>
</div>
